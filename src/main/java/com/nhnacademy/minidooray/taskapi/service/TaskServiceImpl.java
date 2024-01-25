package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.exception.MilestoneNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, MilestoneRepository milestoneRepository,
                           ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
    }

    public List<TaskDto> getTasks() {
        return taskRepository.findAllBy();
    }

    @Override
    public TaskDto getTask(Long taskId) {
        return taskRepository.findByTaskId(taskId);
    }

    @Override
    @Transactional
    public TaskDto createTask(TaskRequest registerRequest) {
        Optional<Milestone> milestone = Optional.empty();
        if (Objects.nonNull(registerRequest.getMilestoneId())) {
            milestone = milestoneRepository.findById(registerRequest.getMilestoneId());
        }
        Optional<Project> project = projectRepository.findById(registerRequest.getProjectId());

        if (project.isEmpty()) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }

        Task task = new Task(registerRequest.getTitle(), registerRequest.getContent(),
                registerRequest.getRegistrantAccount(), LocalDateTime.now(), registerRequest.getExpireDate(),
                milestone.orElse(null), project.get());
        taskRepository.save(task);
        return new TaskDto().entityToDto(task);
    }

    @Override
    @Transactional
    public TaskDto updateTask(Long taskId, TaskRequest taskRequest) {
        Optional<Task> byId = taskRepository.findById(taskId);
        if (byId.isEmpty()) {
            throw new TaskNotExistException("업무가 존재하지 않습니다.");
        }

        Optional<Milestone> milestone = Optional.empty();
        if (Objects.nonNull(taskRequest.getMilestoneId())) {
            if (milestoneRepository.findById(taskRequest.getMilestoneId()).isEmpty()) {
                throw new MilestoneNotExistException("마일스톤이 존재하지 않습니다.");
            }
            milestone = milestoneRepository.findById(taskRequest.getMilestoneId());
        }
        Optional<Project> project = projectRepository.findById(taskRequest.getProjectId());

        if (project.isEmpty()) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }

        Task updatedTask = byId.get().updateTask(
                taskRequest.getTitle(), taskRequest.getContent(), taskRequest.getRegistrantAccount(),
                taskRequest.getExpireDate(), milestone.orElse(null), project.get());

        return new TaskDto().entityToDto(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        Optional<Task> byId = taskRepository.findById(taskId);
        if (byId.isEmpty()) {
            throw new TaskNotExistException("업무가 존재하지 않습니다.");
        }

        taskRepository.deleteById(taskId);
    }
}

package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponse;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponseByProjectId;
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

    public List<TaskResponse> getTasks() {
        return taskRepository.findAllBy();
    }

    @Override
    public List<TaskResponseByProjectId> getTaskByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotExistException();
        }
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public TaskResponse getTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotExistException();
        }
        return taskRepository.findByTaskId(taskId);
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest registerRequest) {
        Optional<Milestone> milestone = Optional.empty();
        if (Objects.nonNull(registerRequest.getMilestoneId())) {
            milestone = milestoneRepository.findById(registerRequest.getMilestoneId());
        }
        Optional<Project> project = projectRepository.findById(registerRequest.getProjectId());

        if (project.isEmpty()) {
            throw new ProjectNotExistException();
        }

        Task task = new Task(registerRequest.getTitle(), registerRequest.getContent(),
                registerRequest.getRegistrantAccount(), LocalDateTime.now(), registerRequest.getExpireDate(),
                milestone.orElse(null), project.get());
        taskRepository.save(task);

        return new TaskResponse().entityToDto(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotExistException::new);

        Optional<Milestone> milestone = Optional.empty();
        if (Objects.nonNull(taskRequest.getMilestoneId())) {
            if (milestoneRepository.findById(taskRequest.getMilestoneId()).isEmpty()) {
                throw new MilestoneNotExistException();
            }
            milestone = milestoneRepository.findById(taskRequest.getMilestoneId());
        }
        Project project = projectRepository.findById(taskRequest.getProjectId())
                .orElseThrow(ProjectNotExistException::new);


        Task updatedTask = task.updateTask(
                taskRequest.getTitle(), taskRequest.getContent(), taskRequest.getRegistrantAccount(),
                taskRequest.getExpireDate(), milestone.orElse(null), project);

        return new TaskResponse().entityToDto(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotExistException::new);
        taskRepository.delete(task);
    }
}

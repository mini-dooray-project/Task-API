package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagResponse;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskTagAlreadyExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskTagNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.TagRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskTagServiceImpl implements TaskTagService {

    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    public TaskTagServiceImpl(TaskTagRepository taskTagRepository, TaskRepository taskRepository,
                              TagRepository tagRepository) {
        this.taskTagRepository = taskTagRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TaskTagResponse> getTaskTagByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotExistException();
        }

        return taskTagRepository.findByTask_taskId(taskId);
    }

    @Override
    @Transactional
    public TaskTagDto createTaskTag(TaskTagDto taskTagDto) {
        TaskTag.Pk pk = new TaskTag.Pk(taskTagDto.getTagId(), taskTagDto.getTaskId());

        Task task = taskRepository.findById(taskTagDto.getTaskId())
                .orElseThrow(TaskNotExistException::new);
        Tag tag = tagRepository.findById(taskTagDto.getTagId())
                .orElseThrow(TagNotExistException::new);
        if (taskTagRepository.findById(pk).isPresent()) {
            throw new TaskTagAlreadyExistException();
        }

        TaskTag taskTag = new TaskTag(pk, task, tag);
        taskTagRepository.save(taskTag);
        return taskTagDto.entityToDto(taskTag);
    }

    @Override
    @Transactional
    public TaskTagDto updateTaskTagByTag(Long taskId, Long targetTagId, TaskTagModifyRequest taskTagModifyRequest) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotExistException();
        }
        if (!tagRepository.existsById(targetTagId)) {
            throw new TagNotExistException();
        }
        if (!tagRepository.existsById(taskTagModifyRequest.getTagId())) {
            throw new TagNotExistException();
        }

        TaskTag byId = taskTagRepository.findById(new TaskTag.Pk(targetTagId, taskId))
                .orElseThrow(TaskTagNotExistException::new);

        TaskTag taskTag = byId.updateTaskTag(taskTagModifyRequest.getTagId());

        return new TaskTagDto().entityToDto(taskTag);
    }

    @Override
    @Transactional
    public void deleteTaskTag(Long taskId, Long targetTagId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotExistException();
        }
        if (!tagRepository.existsById(targetTagId)) {
            throw new TagNotExistException();
        }

        TaskTag.Pk pk = new TaskTag.Pk(targetTagId, taskId);
        TaskTag taskTag = taskTagRepository.findById(pk)
                .orElseThrow(TaskTagNotExistException::new);

        taskTagRepository.delete(taskTag);
    }
}

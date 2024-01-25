package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagRequest;
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
import org.springframework.stereotype.Service;

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
    public TaskTagRequest createTaskTag(TaskTagRequest taskTagRequest) {
        TaskTag.Pk pk = new TaskTag.Pk(taskTagRequest.getTagId(), taskTagRequest.getTaskId());

        Task task = taskRepository.findById(taskTagRequest.getTaskId())
                .orElseThrow(() -> new TaskNotExistException("업무가 존재하지 않습니다."));
        Tag tag = tagRepository.findById(taskTagRequest.getTagId())
                .orElseThrow(() -> new TagNotExistException("태그가 존재하지 않습니다."));
        if (taskTagRepository.findById(pk).isPresent()) {
            throw new TaskTagAlreadyExistException("대응하는 업무-태그가 존재합니다.");
        }

        TaskTag taskTag = new TaskTag(pk, task, tag);
        taskTagRepository.save(taskTag);
        return taskTagRequest.entityToDto(taskTag);
    }

    @Override
    public TaskTagRequest updateTaskTagByTag(Long taskId, Long targetTagId, TaskTagRequest taskTagRequest) {
        TaskTag byId = taskTagRepository.findById(new TaskTag.Pk(targetTagId, taskId))
                .orElseThrow(() -> new TaskTagNotExistException("대응하는 업무-태그가 존재하지 않습니다."));

        TaskTag taskTag = byId.updateTaskTag(taskTagRequest.getTagId());

        return new TaskTagRequest().entityToDto(taskTag);
    }

    @Override
    public void deleteTaskTag(Long taskId, Long targetTagId) {
        TaskTag.Pk pk = new TaskTag.Pk(targetTagId, taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotExistException("업무가 존재하지 않습니다."));
        Tag tag = tagRepository.findById(targetTagId)
                .orElseThrow(() -> new TagNotExistException("태그가 존재하지 않습니다."));
        taskTagRepository.findById(pk).orElseThrow(() -> new TaskTagNotExistException("대응하는 업무-태그가 존재하지 않습니다."));

        taskTagRepository.delete(new TaskTag(pk, task, tag));
    }
}

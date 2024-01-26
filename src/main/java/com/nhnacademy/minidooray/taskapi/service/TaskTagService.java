package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;

public interface TaskTagService {

    TaskTagDto createTaskTag(TaskTagDto taskTagDto);

    TaskTagDto updateTaskTagByTag(Long tagId, Long targetTagId, TaskTagDto taskTagDto);

    void deleteTaskTag(Long taskId, Long targetTagId);
}

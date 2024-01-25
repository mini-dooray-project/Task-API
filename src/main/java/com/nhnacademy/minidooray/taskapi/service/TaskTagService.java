package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagRequest;

public interface TaskTagService {

    TaskTagRequest createTaskTag(TaskTagRequest taskTagRequest);

    TaskTagRequest updateTaskTagByTag(Long tagId, Long targetTagId, TaskTagRequest taskTagRequest);

    void deleteTaskTag(Long taskId, Long targetTagId);
}

package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagResponse;
import java.util.List;

public interface TaskTagService {

    TaskTagDto createTaskTag(TaskTagDto taskTagDto);

    TaskTagDto updateTaskTagByTag(Long tagId, Long targetTagId, TaskTagModifyRequest taskTagModifyRequest);

    void deleteTaskTag(Long taskId, Long targetTagId);

    List<TaskTagResponse> getTaskTagByTaskId(Long taskId);
}

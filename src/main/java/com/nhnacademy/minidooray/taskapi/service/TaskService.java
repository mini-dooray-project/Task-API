package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks();
    TaskDto getTask(Long taskId);
    TaskDto createTask(TaskRequest taskRequest);

    TaskDto updateTask(Long taskId, TaskRequest taskRequest);

    TaskDto deleteTask(Long taskId);
}

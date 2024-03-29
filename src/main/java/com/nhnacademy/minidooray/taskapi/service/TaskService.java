package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponse;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponseByProjectId;
import java.util.List;

public interface TaskService {
    List<TaskResponse> getTasks();

    List<TaskResponseByProjectId> getTaskByProjectId(Long projectId);

    TaskResponse getTask(Long taskId);

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse updateTask(Long taskId, TaskRequest taskRequest);

    void deleteTask(Long taskId);
}

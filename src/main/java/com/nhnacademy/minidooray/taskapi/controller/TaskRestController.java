package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.DeleteResponse;
import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.service.TaskService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        TaskDto taskDto = taskService.createTask(taskRequest);
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequest taskRequest) {
        TaskDto taskDto = taskService.updateTask(taskId, taskRequest);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public DeleteResponse deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new DeleteResponse("OK");
    }
}

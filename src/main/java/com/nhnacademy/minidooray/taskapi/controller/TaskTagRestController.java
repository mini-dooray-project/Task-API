package com.nhnacademy.minidooray.taskapi.controller;


import com.nhnacademy.minidooray.taskapi.domain.DeleteResponse;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagRequest;
import com.nhnacademy.minidooray.taskapi.service.TaskTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskTagRestController {

    private final TaskTagService taskTagService;

    public TaskTagRestController(TaskTagService taskTagService) {
        this.taskTagService = taskTagService;
    }

    @PostMapping("/tasks/tags")
    public ResponseEntity<TaskTagRequest> createTaskTag(@RequestBody TaskTagRequest taskTagRequest) {
        TaskTagRequest taskTag = taskTagService.createTaskTag(taskTagRequest);
        return new ResponseEntity<>(taskTag, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{taskId}/tags/{targetTagId}")
    public ResponseEntity<TaskTagRequest> updateTaskTagByTag(@PathVariable Long taskId, @PathVariable Long targetTagId,
                                                             @RequestBody TaskTagRequest taskTagRequest) {
        // Tag를 업데이트합니다
        TaskTagRequest taskTag = taskTagService.updateTaskTagByTag(taskId, targetTagId, taskTagRequest);
        return new ResponseEntity<>(taskTag, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{taskId}/tags/{targetTagId}")
    public DeleteResponse deleteTaskTag(@PathVariable Long taskId, @PathVariable Long targetTagId) {
        taskTagService.deleteTaskTag(taskId, targetTagId);
        return new DeleteResponse("OK");
    }
}

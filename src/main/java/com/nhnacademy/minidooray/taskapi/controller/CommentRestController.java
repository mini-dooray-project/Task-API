package com.nhnacademy.minidooray.taskapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    @GetMapping("/{taskId}")
    public void getCommentsByTask(@PathVariable Long taskId) {

    }
}

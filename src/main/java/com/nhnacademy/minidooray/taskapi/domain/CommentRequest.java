package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest {
    private Long taskId;
    private String registrantAccount;
    private String content;

    public CommentRequest() {
    }
}

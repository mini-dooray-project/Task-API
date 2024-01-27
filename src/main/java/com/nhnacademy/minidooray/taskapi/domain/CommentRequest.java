package com.nhnacademy.minidooray.taskapi.domain;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequest {
    private Long taskId;
    @NotBlank
    private String registrantAccount;
    @NotBlank
    private String content;

    public CommentRequest() {
    }

    public CommentRequest(Long taskId, String registrantAccount, String content) {
        this.taskId = taskId;
        this.registrantAccount = registrantAccount;
        this.content = content;
    }
}

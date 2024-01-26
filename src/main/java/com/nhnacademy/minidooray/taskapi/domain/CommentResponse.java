package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponse {
    private Long commentId;
    private Long taskId;
    private String registrantAccount;
    private LocalDateTime createdDate;
    private String content;

    public CommentResponse entityToDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.taskId = comment.getTask().getTaskId();
        this.registrantAccount = comment.getRegistrantAccount();
        this.createdDate = comment.getCreatedDate();
        this.content = comment.getContent();

        return this;
    }

    public CommentResponse() {
    }

    public CommentResponse(Long commentId, Long taskId, String registrantAccount, LocalDateTime createdDate,
                           String content) {
        this.commentId = commentId;
        this.taskId = taskId;
        this.registrantAccount = registrantAccount;
        this.createdDate = createdDate;
        this.content = content;
    }
}

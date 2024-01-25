package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.CommentRequest;
import com.nhnacademy.minidooray.taskapi.domain.CommentResponse;
import java.util.List;

public interface CommentService {

    List<CommentResponse> getCommentsByTask(Long taskId);

    CommentResponse createComment(CommentRequest commentRequest);

    CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

    void deleteComment(Long commentId);
}

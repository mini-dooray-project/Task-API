package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.CommentRequest;
import com.nhnacademy.minidooray.taskapi.domain.CommentResponse;
import com.nhnacademy.minidooray.taskapi.entity.Comment;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.exception.CommentNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<CommentResponse> getCommentsByTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotExistException();
        }

        return commentRepository.findByTask_TaskId(taskId);
    }

    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest) {
        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(TaskNotExistException::new);
        Comment comment = new Comment(task, commentRequest.getRegistrantAccount(), LocalDateTime.now(),
                commentRequest.getContent());
        commentRepository.save(comment);

        return new CommentResponse().entityToDto(comment);
    }

    @Override
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        Task task = taskRepository.findById(commentRequest.getTaskId())
                .orElseThrow(TaskNotExistException::new);
        Comment comment =
                commentRepository.findById(commentId).orElseThrow(CommentNotExistException::new);

        Comment updatedComment =
                comment.updateComment(task, commentRequest.getRegistrantAccount(), commentRequest.getContent());

        return new CommentResponse().entityToDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment =
                commentRepository.findById(commentId).orElseThrow(CommentNotExistException::new);

        commentRepository.delete(comment);
    }
}

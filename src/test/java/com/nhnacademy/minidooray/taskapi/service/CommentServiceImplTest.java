package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.CommentRequest;
import com.nhnacademy.minidooray.taskapi.domain.CommentResponse;
import com.nhnacademy.minidooray.taskapi.entity.Comment;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void getCommentsByTask() {
        Long taskId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(commentRepository.findByTask_taskId(anyLong())).thenReturn(List.of(new CommentResponse()));

        List<CommentResponse> commentsByTask = commentService.getCommentsByTask(taskId);

        assertEquals(1, commentsByTask.size());
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(commentRepository, times(1)).findByTask_taskId(anyLong());
    }

    @Test
    void getCommentsByTask_thenThrowTaskNotExistException() throws Exception {
        Long taskId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TaskNotExistException.class, () -> commentService.getCommentsByTask(taskId));
        verify(taskRepository, times(1)).existsById(anyLong());
    }

    @Test
    void createComment() {
        CommentRequest commentRequest = new CommentRequest(1L, "account", "content");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));

        CommentResponse comment = commentService.createComment(commentRequest);

        assertNotNull(comment);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void updateComment() {
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest(1L, "account", "content");

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(new Comment()));

        CommentResponse comment = commentService.updateComment(commentId, commentRequest);

        assertNotNull(comment);
        verify(taskRepository, times(1)).findById(anyLong());
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteComment() {
        Long commentId = 1L;

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(new Comment()));
        doNothing().when(commentRepository).delete(any());

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }
}
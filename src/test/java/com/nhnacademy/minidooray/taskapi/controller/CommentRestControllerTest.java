package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.CommentRequest;
import com.nhnacademy.minidooray.taskapi.domain.CommentResponse;
import com.nhnacademy.minidooray.taskapi.exception.CommentNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.CommentService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @Test
    void getCommentsByTask() throws Exception {
        given(commentService.getCommentsByTask(anyLong())).willReturn(
                List.of(new CommentResponse(1L, 1L, "account", LocalDateTime.now(), "content")));

        mockMvc.perform(get("/api/comments/{taskId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].commentId", equalTo(1)))
                .andExpect(jsonPath("$[0].taskId", equalTo(1)))
                .andExpect(jsonPath("$[0].registrantAccount", equalTo("account")))
                .andExpect(jsonPath("$[0].content", equalTo("content")));
    }

    @Test
    void getCommentsByTask_thenThrowTaskNotExistException() throws Exception {
        given(commentService.getCommentsByTask(anyLong())).willThrow(TaskNotExistException.class);

        mockMvc.perform(get("/api/comments/{taskId}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    void createComment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(commentService.createComment(any())).willReturn(
                new CommentResponse(1L, 1L, "account", LocalDateTime.now(), "content"));

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentRequest(1L, "test", "test"))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId", equalTo(1)))
                .andExpect(jsonPath("$.taskId", equalTo(1)))
                .andExpect(jsonPath("$.registrantAccount", equalTo("account")))
                .andExpect(jsonPath("$.content", equalTo("content")));
    }

    @Test
    void createCommentValidationTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(commentService.createComment(any())).willThrow(ValidationException.class);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void createCommentTaskNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(commentService.createComment(any())).willThrow(TaskNotExistException.class);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentRequest(1L, "test", "test"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    void updateComment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(commentService.updateComment(anyLong(), any())).willReturn(
                new CommentResponse(1L, 1L, "account", LocalDateTime.now(), "content"));

        mockMvc.perform(put("/api/comments/{commentId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentRequest(1L, "test", "test"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId", equalTo(1)))
                .andExpect(jsonPath("$.taskId", equalTo(1)))
                .andExpect(jsonPath("$.registrantAccount", equalTo("account")))
                .andExpect(jsonPath("$.content", equalTo("content")));
    }

    @Test
    void updateCommentValidationTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(commentService.updateComment(anyLong(), any())).willThrow(ValidationException.class);

        mockMvc.perform(put("/api/comments/{commentId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void updateComment_thenThrowCommentNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(commentService.updateComment(anyLong(), any())).willThrow(CommentNotExistException.class);

        mockMvc.perform(put("/api/comments/{commentId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommentRequest(1L, "account", "content"))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommentNotExistException));
    }

    @Test
    void deleteComment() throws Exception {
        doNothing().when(commentService).deleteComment(anyLong());

        mockMvc.perform(delete("/api/comments/{commentId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    void deleteComment_CommentNotExistException() throws Exception {
        doThrow(CommentNotExistException.class).when(commentService).deleteComment(anyLong());

        mockMvc.perform(delete("/api/comments/{commentId}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CommentNotExistException));
    }
}
package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponse;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TaskRestControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    @Order(1)
    @DisplayName("task 목록 가져오기")
    void getTasks() throws Exception {
        given(taskService.getTasks()).willReturn(List.of(new TaskResponse(1L, "testTitle", "test content", "test user", LocalDateTime.now(), LocalDateTime.now(), 1L, null)));
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", equalTo("testTitle")))
                .andExpect(jsonPath("$[0].content", equalTo("test content")));
    }

    @Test
    @Order(2)
    @DisplayName("1번째 task 가져오기")
    void getTask() throws Exception {
        given(taskService.getTask(1L)).willReturn(new TaskResponse(1L, "testTitle", "test content", "testUser", LocalDateTime.now(), LocalDateTime.now(), 1L, null));
        mockMvc.perform(get("/api/tasks/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", equalTo(1)))
                .andExpect(jsonPath("$.content", equalTo("test content")))
                .andExpect(jsonPath("$.title", equalTo("testTitle")))
                .andExpect(jsonPath("$.registrantAccount", equalTo("testUser")));
    }

    @Test
    @Order(3)
    @DisplayName("업무 등록하기")
    void createTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        TaskRequest taskRequest = new TaskRequest(null, 1L, "testTitle", "testContent", "testUser", null);

        TaskResponse taskResponse = new TaskResponse(1L, "testTitle", "testContent", "testUser", null, null, 1L, 1L);
        given(taskService.createTask(taskRequest)).willReturn(taskResponse);
        mockMvc.perform(post("/api/tasks")
                        .content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", equalTo("testContent")))
                .andExpect(jsonPath("$.title", equalTo("testTitle")))
                .andExpect(jsonPath("$.registrantAccount", equalTo("testUser")));
    }

    @Test
    @Order(4)
    @DisplayName("업무 수정")
    void updateTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskRequest taskRequest = new TaskRequest(null, 1L, "testTitle", "contentTest", "testUser", null);
        TaskResponse taskResponse = new TaskResponse(1L, "changeTitle", "changeContentTest", "testUser", null, null, 1L, 1L);

        given(taskService.updateTask(1L, taskRequest)).willReturn(taskResponse);
        mockMvc.perform(put("/api/tasks/{taskId}", 1L)
                        .content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("changeTitle")));
    }

    @Test
    @Order(5)
    @DisplayName("특정 업무 삭제")
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);
        mockMvc.perform(delete("/api/tasks/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    @Order(6)
    @DisplayName("validation exception test")
    void createValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        TaskRequest taskRequest = new TaskRequest(null, 1L, " ", "testContent", " ", null);

        TaskResponse taskResponse = new TaskResponse(1L, "testTitle", "testContent", "testUser", null, null, 1L, 1L);
        given(taskService.createTask(taskRequest)).willThrow(ValidationException.class);

        mockMvc.perform(post("/api/tasks")
                        .content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }
}
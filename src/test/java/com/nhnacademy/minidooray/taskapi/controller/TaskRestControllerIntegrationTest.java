package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc
class TaskRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("task 목록 가져오기")
    void getTasks() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].taskId", equalTo(11)))
                .andExpect(jsonPath("$[0].content", equalTo("test")));
    }

    @Test
    @Order(2)
    @DisplayName("11번째 task 가져오기")
    void getTask() throws Exception {
        mockMvc.perform(get("/api/tasks/{taskId}", 11L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", equalTo(11)))
                .andExpect(jsonPath("$.content", equalTo("test")))
                .andExpect(jsonPath("$.title", equalTo("testaaaa")))
                .andExpect(jsonPath("$.registrantAccount", equalTo("testUser")));
    }

    @Test
    @Order(3)
    @DisplayName("업무 등록하기")
    void createTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        TaskRequest taskRequest = new TaskRequest(null, 1L, "testTitle", "integration Test", "testUser", null);
        mockMvc.perform(post("/api/tasks")
                        .content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", equalTo("integration Test")))
                .andExpect(jsonPath("$.title", equalTo("testTitle")))
                .andExpect(jsonPath("$.registrantAccount", equalTo("testUser")));
    }

    @Test
    @Order(4)
    @DisplayName("업무 수정")
    void updateTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskRequest taskRequest = new TaskRequest(null, 1L, "changeTitle", "change content Test", "testUser", null);
        mockMvc.perform(put("/api/tasks/{taskId}", 11L)
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
        mockMvc.perform(delete("/api/tasks/{taskId}", 11L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    @Order(6)
    @DisplayName("validation 테스트")
    void createTaskValidationExcepitionTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        TaskRequest taskRequest = new TaskRequest(null, 1L, "", "integration Test", "testUser", null);
        mockMvc.perform(post("/api/tasks")
                        .content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }
}
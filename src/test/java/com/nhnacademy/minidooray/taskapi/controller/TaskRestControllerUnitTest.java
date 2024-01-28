package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponse;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponseByProjectId;
import com.nhnacademy.minidooray.taskapi.exception.MilestoneNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TaskRestControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Test
    @DisplayName("task 목록 가져오기")
    void getTasks() throws Exception {
        given(taskService.getTasks()).willReturn(
                List.of(new TaskResponse(1L, "testTitle", "test content", "test user", LocalDateTime.now(),
                        LocalDateTime.now(), 1L, null, null, null, null)));

        mockMvc.perform(get("/api/tasks")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", equalTo("testTitle")))
                .andExpect(jsonPath("$[0].content", equalTo("test content")));
    }

    @Test
    @DisplayName("{taskId}번째 task 가져오기")
    void getTask() throws Exception {
        given(taskService.getTask(1L)).willReturn(
                new TaskResponse(1L, "testTitle", "test content", "testUser", LocalDateTime.now(), LocalDateTime.now(),
                        1L, null, null, null, null));
        mockMvc.perform(get("/api/tasks/{taskId}", 1L)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", equalTo(1))).andExpect(jsonPath("$.content", equalTo("test content")))
                .andExpect(jsonPath("$.title", equalTo("testTitle")))
                .andExpect(jsonPath("$.registrantAccount", equalTo("testUser")));
    }

    @Test
    void getTask_thenThrowTaskNotExistException() throws Exception {
        given(taskService.getTask(1L)).willThrow(TaskNotExistException.class);

        mockMvc.perform(get("/api/tasks/{taskId}", 1L)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    void getTasksByProjectId() throws Exception {
        given(taskService.getTaskByProjectId(anyLong())).willReturn(
                List.of(new TaskResponseByProjectId(1L, "testTitle",  "testUser", "name")));

        mockMvc.perform(get("/api/tasks/projects/{projectId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].taskId", equalTo(1)))
                .andExpect(jsonPath("$[0].taskTitle", equalTo("testTitle")))
                .andExpect(jsonPath("$[0].registrantAccount", equalTo("testUser")))
                .andExpect(jsonPath("$[0].milestoneName", equalTo("name")));
    }

    @Test
    void getTasksByProjectId_thenThrowProjectNotExistException() throws Exception {
        given(taskService.getTaskByProjectId(anyLong())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(get("/api/tasks/projects/{projectId}", 1L))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    @DisplayName("task 등록하기")
    void createTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskRequest taskRequest = new TaskRequest(null, 1L, "testTitle", "testContent", "testUser", null);
        TaskResponse taskResponse =
                new TaskResponse(1L, "testTitle", "testContent", "testUser", LocalDateTime.now(), null, 1L, null, null,
                        null, null);

        given(taskService.createTask(any())).willReturn(taskResponse);

        mockMvc.perform(post("/api/tasks").content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", equalTo("testContent")))
                .andExpect(jsonPath("$.title", equalTo("testTitle")))
                .andExpect(jsonPath("$.registrantAccount", equalTo("testUser")));
    }

    @Test
    @DisplayName("validation exception test")
    void createValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        given(taskService.createTask(any())).willThrow(ValidationException.class);

        mockMvc.perform(post("/api/tasks").content(mapper.writeValueAsString(new ProjectRequest()))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void createTest_thenThrowProjectNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        given(taskService.createTask(any())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(post("/api/tasks").content(
                                mapper.writeValueAsString(new TaskRequest(1L, 1L, "title", "content", "account", LocalDateTime.now())))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }


    @Test
    @DisplayName("task 수정")
    void updateTask() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskRequest taskRequest = new TaskRequest(null, 1L, "testTitle", "contentTest", "testUser", null);
        TaskResponse taskResponse =
                new TaskResponse(1L, "changeTitle", "changeContentTest", "testUser", LocalDateTime.now(), null, 1L,
                        null, null, null, null);

        given(taskService.updateTask(anyLong(), any())).willReturn(taskResponse);
        mockMvc.perform(put("/api/tasks/{taskId}", 1L).content(mapper.writeValueAsString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("changeTitle")));
    }

    @Test
    void updateTask_thenThrowValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        given(taskService.updateTask(anyLong(), any())).willThrow(ValidationException.class);
        mockMvc.perform(put("/api/tasks/{taskId}", 1).content(objectMapper.writeValueAsString(new TaskRequest()))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void updateTask_thenThrowMilestoneNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        given(taskService.updateTask(anyLong(), any())).willThrow(MilestoneNotExistException.class);


        mockMvc.perform(put("/api/tasks/{taskId}", 1L).content(objectMapper.writeValueAsString(
                                new TaskRequest(1L, 1L, "title", "content", "account", LocalDateTime.now())))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MilestoneNotExistException));
    }

    @Test
    void updateTask_thenThrowProjectNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        given(taskService.updateTask(anyLong(), any())).willThrow(ProjectNotExistException.class);


        mockMvc.perform(put("/api/tasks/{taskId}", 1L).content(objectMapper.writeValueAsString(
                                new TaskRequest(1L, 1L, "title", "content", "account", LocalDateTime.now())))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    @DisplayName("task 삭제")
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);
        mockMvc.perform(delete("/api/tasks/{taskId}", 1L)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    void deleteTask_thenThrowTaskNotExistException() throws Exception {
        doThrow(TaskNotExistException.class).when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}", 1L)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

}
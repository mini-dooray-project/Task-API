package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectStatusNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@WebMvcTest(ProjectRestController.class)
class ProjectRestControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @Test
    void getProjects() throws Exception {
        List<ProjectResponse> projectResponses = List.of(new ProjectResponse(1L, 2L, "test", "test"));

        given(projectService.getProjects()).willReturn(projectResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].projectName", equalTo("test")));
    }

    @Test
    void getProject() throws Exception {
        ProjectResponse projectResponse = new ProjectResponse(1L, 2L, "test",  "test");

        given(projectService.getProject(anyLong())).willReturn(projectResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/{projectId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    void getProject_thenThrowProjectNotExistException() throws Exception {
        given(projectService.getProject(anyLong())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/{projectId}", 1L))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    void createProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectResponse projectResponse = new ProjectResponse(10L, 1L, "test",  "test");
        ProjectRequest projectRequest = new ProjectRequest(1L, "test");

        given(projectService.createProject(any())).willReturn(projectResponse);

        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    void createProjectValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectResponse projectResponse = new ProjectResponse(10L, 1L, "test",  "test");

        given(projectService.createProject(any())).willReturn(projectResponse);

        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(new ProjectRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void createProject_thenThrowProjectStatusNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        given(projectService.createProject(any())).willThrow(ProjectStatusNotExistException.class);

        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(new ProjectRequest(1L, "name")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        result -> assertTrue(result.getResolvedException() instanceof ProjectStatusNotExistException));
    }

    @Test
    void updateProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectRequest projectRequest = new ProjectRequest(2L, "test");
        ProjectResponse projectResponse = new ProjectResponse(7L, 2L, "test",  "test");

        given(projectService.updateProject(anyLong(), any())).willReturn(projectResponse);

        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    void updateProjectValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        given(projectService.updateProject(anyLong(), any())).willThrow(ValidationException.class);

        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(new ProjectRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void updateProject_thenThrowProjectStatusNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectService.updateProject(anyLong(), any())).willThrow(ProjectStatusNotExistException.class);

        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(objectMapper.writeValueAsString(new ProjectRequest(1L, "name")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectStatusNotExistException));
    }

    @Test
    void updateProject_thenThrowProjectNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectService.updateProject(anyLong(), any())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(objectMapper.writeValueAsString(new ProjectRequest(1L, "name")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    void deleteProject() throws Exception {
        doNothing().when(projectService).deleteProject(6L);

        mockMvc.perform(delete("/api/projects/{projectId}", 6L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    void deleteProject_ProjectNotExistException() throws Exception {
        doThrow(ProjectNotExistException.class).when(projectService).deleteProject(anyLong());

        mockMvc.perform(delete("/api/projects/{projectId}", 6L))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }
}
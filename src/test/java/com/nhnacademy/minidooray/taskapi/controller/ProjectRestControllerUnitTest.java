package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
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

        given(projectService.getProject(1L)).willReturn(projectResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/{projectId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));

    }

    @Test
    void createProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectResponse projectResponse = new ProjectResponse(10L, 1L, "test",  "test");
        ProjectRequest projectRequest = new ProjectRequest(1L, "test");

        given(projectService.createProject(projectRequest)).willReturn(projectResponse);

        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    void updateProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectRequest projectRequest = new ProjectRequest(2L, "test");
        ProjectResponse projectResponse = new ProjectResponse(7L, 2L, "test",  "test");

        given(projectService.updateProject(7L, projectRequest)).willReturn(projectResponse);

        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
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
    void createProjectValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectResponse projectResponse = new ProjectResponse(10L, 1L, "test",  "test");
        ProjectRequest projectRequest = new ProjectRequest(1L, "");

        given(projectService.createProject(projectRequest)).willReturn(projectResponse);

        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProjectValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ProjectRequest projectRequest = new ProjectRequest(2L, "");
        ProjectResponse projectResponse = new ProjectResponse(7L, 2L, "test",  "test");

        given(projectService.updateProject(7L, projectRequest)).willReturn(projectResponse);

        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
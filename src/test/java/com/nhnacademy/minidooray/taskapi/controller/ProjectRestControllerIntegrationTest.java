package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void getProject() throws Exception {
        mockMvc.perform(get("/api/projects/{projectId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    @Transactional
    void createProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectRequest projectRequest = new ProjectRequest(2L, "create-test");
        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("create-test")));
    }

    @Test
    @Transactional
    void updateProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectRequest projectRequest = new ProjectRequest(2L, "test");
        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    @Transactional
    void deleteProject() throws Exception {
        mockMvc.perform(delete("/api/projects/{projectId}", 8L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));

    }

    @Test
    @Transactional
    void createProjectValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectRequest projectResponse = new ProjectRequest(2L, "");
        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(projectResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updateProjectValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectRequest projectRequest = new ProjectRequest(2L, "");
        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(projectRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

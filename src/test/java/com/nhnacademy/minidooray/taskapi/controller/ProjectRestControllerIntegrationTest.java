package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @Order(1)
    void getProjects() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].projectName", equalTo("test")));
    }

    @Test
    @Order(2)
    void getProject() throws Exception {
        mockMvc.perform(get("/api/projects/{projectId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    void createProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectResponse projectResponse = new ProjectResponse(6L, 2L, "create-test");
        mockMvc.perform(post("/api/projects")
                        .content(mapper.writeValueAsString(projectResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("create-test")));
    }

    @Test
    void updateProject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ProjectResponse projectResponse = new ProjectResponse(7L, 2L, "test");
        mockMvc.perform(put("/api/projects/{projectId}", 7L)
                        .content(mapper.writeValueAsString(projectResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectName", equalTo("test")));
    }

    @Test
    void deleteProject() throws Exception {
        mockMvc.perform(delete("/api/projects/{projectId}", 8L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));

    }
}
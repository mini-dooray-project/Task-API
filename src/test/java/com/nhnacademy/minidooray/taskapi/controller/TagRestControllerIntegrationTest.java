package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TagRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getTags() throws Exception {
        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$[0].tagName", equalTo("java")));
    }

    @Test
    void getTag() throws Exception {
        mockMvc.perform(get("/api/tags/{tagId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$[0].tagName", equalTo("java")));

    }

    @Test
    @Transactional
    void createTag() throws Exception {



//        mockMvc.perform(post("/api/tags")
//                .content()
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect()

    }

    @Test
    @Transactional
    void updateTag() {
    }

    @Test
    @Transactional
    void deleteTag() {
    }
}
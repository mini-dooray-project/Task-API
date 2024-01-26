package com.nhnacademy.minidooray.taskapi.controller;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import com.nhnacademy.minidooray.taskapi.service.TagService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TagRestControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    @Test
    void getTags() throws Exception {
        given(tagService.getTags()).willReturn(List.of(new TagResponse(1L, 1L, "java")));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$[0].tagName", equalTo("java")));
    }

    @Test
    void getTag() throws Exception {

        given(tagService.getTag(anyLong())).willReturn(new TagResponse(1L, 1L, "java"));

        mockMvc.perform(get("/api/tags/{tagId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tagId", equalTo(1)))
                .andExpect(jsonPath("$.tagName", equalTo("java")));

    }

    @Test
    void createTag() throws Exception {
        TagResponse tagResponse = new TagResponse(1L, 1L, "java");
        given(tagService.createTag(any())).willReturn(tagResponse);

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/tags")
                        .content(objectMapper.writeValueAsString(tagResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tagId", equalTo(1)))
                .andExpect(jsonPath("$.tagName", equalTo("java")));

    }

    @Test
    void updateTag() {
    }

    @Test
    void deleteTag() {
    }
}

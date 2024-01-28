package com.nhnacademy.minidooray.taskapi.controller;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.TagRequest;
import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.TagService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    void getTag_thenThrowTagNotExistException() throws Exception {

        given(tagService.getTag(anyLong())).willThrow(TagNotExistException.class);

        mockMvc.perform(get("/api/tags/{tagId}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TagNotExistException));
    }

    @Test
    void getTagByProjectId() throws Exception{
        given(tagService.getTagByProjectId(anyLong())).willReturn(List.of(new TagResponse(1L, 1L, "java")));

        mockMvc.perform(get("/api/tags/project/{projectId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$[0].projectId", equalTo(1)))
                .andExpect(jsonPath("$[0].tagName", equalTo("java")));
    }

    @Test
    void createTag() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TagResponse tagResponse = new TagResponse(1L, 1L, "java");
        given(tagService.createTag(any())).willReturn(tagResponse);

        mockMvc.perform(post("/api/tags")
                        .content(objectMapper.writeValueAsString(tagResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tagId", equalTo(1)))
                .andExpect(jsonPath("$.tagName", equalTo("java")));

    }
    @Test
    void createTagValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(tagService.createTag(any())).willThrow(ValidationException.class);

        mockMvc.perform(post("/api/tags")
                        .content(mapper.writeValueAsString(new TagRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void createTag_thenThrowTagNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(tagService.createTag(any())).willThrow(TagNotExistException.class);

        mockMvc.perform(post("/api/tags")
                        .content(objectMapper.writeValueAsString(new TagRequest(1L, "test")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TagNotExistException));
    }

    @Test
    void updateTag() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TagResponse tagResponse = new TagResponse(1L, 1L, "java");
        given(tagService.updateTag(anyLong(), any())).willReturn(tagResponse);

        mockMvc.perform(put("/api/tags/{tagId}", 1)
                        .content(objectMapper.writeValueAsString(tagResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tagId", equalTo(1)))
                .andExpect(jsonPath("$.tagName", equalTo("java")));
    }

    @Test
    void updateTagValidationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(tagService.updateTag(anyLong(), any())).willThrow(ValidationException.class);

        mockMvc.perform(put("/api/tags/{tagId}", 1)
                        .content(mapper.writeValueAsString(new TagRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void updateTag_thenThrowProjectNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(tagService.updateTag(anyLong(), any())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(put("/api/tags/{tagId}", 1)
                        .content(mapper.writeValueAsString(new TagRequest(1L, "name")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    void updateTag_thenThrowTagNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(tagService.updateTag(anyLong(), any())).willThrow(TagNotExistException.class);

        mockMvc.perform(put("/api/tags/{tagId}", 1)
                        .content(mapper.writeValueAsString(new TagRequest(1L, "name")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TagNotExistException));
    }

    @Test
    void deleteTag() throws Exception {
        doNothing().when(tagService).deleteTag(anyLong());

        mockMvc.perform(delete("/api/tags/{tagId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    void deleteTag_thenThrowTagNotExistException() throws Exception {
        doThrow(TagNotExistException.class).when(tagService).deleteTag(anyLong());

        mockMvc.perform(delete("/api/tags/{tagId}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TagNotExistException));
    }
}

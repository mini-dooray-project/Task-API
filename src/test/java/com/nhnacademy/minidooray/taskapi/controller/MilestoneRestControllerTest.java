package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneRequest;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.MilestoneService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MilestoneRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MilestoneService milestoneService;

    @Test
    void getMilestones() throws Exception {
        given(milestoneService.getMilestones()).willReturn(
                List.of(new MilestoneResponse(1L, 1L, "name", LocalDateTime.now(), LocalDateTime.now())));

        mockMvc.perform(get("/api/milestones")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].milestoneId", equalTo(1))).andExpect(jsonPath("$[0].projectId", equalTo(1)))
                .andExpect(jsonPath("$[0].milestoneName", equalTo("name")));
    }

    @Test
    void getMilestone() throws Exception {
        given(milestoneService.getMilestone(anyLong())).willReturn(
                new MilestoneResponse(1L, 1L, "name", LocalDateTime.now(), LocalDateTime.now()));

        mockMvc.perform(get("/api/milestones/{milestoneId}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.milestoneId", equalTo(1))).andExpect(jsonPath("$.projectId", equalTo(1)))
                .andExpect(jsonPath("$.milestoneName", equalTo("name")));
    }

    @Test
    void createMilestone() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(milestoneService.createMilestone(any())).willReturn(
                new MilestoneResponse(1L, 1L, "name", LocalDateTime.now(), LocalDateTime.now()));

        mockMvc.perform(post("/api/milestones").content(objectMapper.writeValueAsString(new MilestoneRequest()))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.milestoneId", equalTo(1))).andExpect(jsonPath("$.projectId", equalTo(1)))
                .andExpect(jsonPath("$.milestoneName", equalTo("name")));
    }

    @Test
    void updateMilestone() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(milestoneService.updateMilestone(anyLong(), any())).willReturn(
                new MilestoneResponse(1L, 1L, "name", LocalDateTime.now(), LocalDateTime.now()));

        mockMvc.perform(put("/api/milestones/{milestoneId}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new MilestoneRequest()))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.milestoneId", equalTo(1))).andExpect(jsonPath("$.projectId", equalTo(1)))
                .andExpect(jsonPath("$.milestoneName", equalTo("name")));
    }

    @Test
    void deleteMilestone() throws Exception {
        doNothing().when(milestoneService).deleteMilestone(anyLong());

        mockMvc.perform(delete("/api/milestones/{milestoneId}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    void createMilestoneValidation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(milestoneService.createMilestone(any())).willThrow(ValidationException.class);

        mockMvc.perform(post("/api/milestones").content(objectMapper.writeValueAsString(new MilestoneRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    void updateMilestoneValidation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(milestoneService.updateMilestone(anyLong(), any())).willThrow(ValidationException.class);

        mockMvc.perform(
                put("/api/milestones/{milestoneId}", 1).content(objectMapper.writeValueAsString(new MilestoneRequest()))
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}
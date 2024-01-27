package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import com.nhnacademy.minidooray.taskapi.exception.AuthorityNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.MemberNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ValidationException;
import com.nhnacademy.minidooray.taskapi.service.ProjectMemberService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProjectMemberRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectMemberService projectMemberService;

    @Test
    void getMembersByProjectId() throws Exception {
        given(projectMemberService.getMembersByProject(anyLong())).willReturn(
                List.of(new ProjectMemberResponse("user", 1L, "name", 1L, "activate")));

        mockMvc.perform(get("/api/members/projects/{projectId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].memberId", equalTo("user")))
                .andExpect(jsonPath("$[0].projectId", equalTo(1)))
                .andExpect(jsonPath("$[0].projectName", equalTo("name")))
                .andExpect(jsonPath("$[0].authorityId", equalTo(1)))
                .andExpect(jsonPath("$[0].authorityName", equalTo("activate")));
    }

    @Test
    void getMembersByProjectId_thenThrowProjectNotExistException() throws Exception {
        given(projectMemberService.getMembersByProject(anyLong())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(get("/api/members/projects/{projectId}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMembersByMemberId() throws Exception {
        given(projectMemberService.getMemberByMemberId(anyString())).willReturn(
                List.of(new ProjectMemberResponse("user", 1L, "name", 1L, "activate")));

        mockMvc.perform(get("/api/members/{memberId}", "userId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].memberId", equalTo("user")))
                .andExpect(jsonPath("$[0].projectId", equalTo(1)))
                .andExpect(jsonPath("$[0].projectName", equalTo("name")))
                .andExpect(jsonPath("$[0].authorityId", equalTo(1)))
                .andExpect(jsonPath("$[0].authorityName", equalTo("activate")));

    }

    @Test
    void getMembersByMemberId_thenThrowMemberNotExistException() throws Exception {
        given(projectMemberService.getMemberByMemberId(anyString())).willThrow(MemberNotExistException.class);

        mockMvc.perform(get("/api/members/{memberId}", "user"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MemberNotExistException));
    }

    @Test
    void createMember() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.createMember(any())).willReturn(
                new ProjectMemberResponse("user", 1L, "name", 1L, "activate"));

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest("id", 1L, 1L))))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId", equalTo("user")))
                .andExpect(jsonPath("$.projectId", equalTo(1)))
                .andExpect(jsonPath("$.projectName", equalTo("name")))
                .andExpect(jsonPath("$.authorityId", equalTo(1)))
                .andExpect(jsonPath("$.authorityName", equalTo("activate")));
    }

    @Test
    void createMember_thenThrowValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.createMember(any())).willThrow(ValidationException.class);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void createMember_thenThrowProjectNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.createMember(any())).willThrow(ProjectNotExistException.class);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest("id", 1L, 1L))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    void createMember_thenThrowAuthorityNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.createMember(any())).willThrow(AuthorityNotExistException.class);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest("id", 1L, 1L))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthorityNotExistException));
    }

    @Test
    void updateMember() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.updateMember(anyString(), anyLong(), any())).willReturn(
                new ProjectMemberResponse("user", 1L, "name", 1L, "activate"));

        mockMvc.perform(put("/api/members/{memberId}/projects/{projectId}", "user", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberModifyRequest(1L))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.memberId", equalTo("user")))
                .andExpect(jsonPath("$.projectId", equalTo(1)))
                .andExpect(jsonPath("$.projectName", equalTo("name")))
                .andExpect(jsonPath("$.authorityId", equalTo(1)))
                .andExpect(jsonPath("$.authorityName", equalTo("activate")));
    }

    @Test
    void updateMember_thenThrowProjectNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.updateMember(anyString(), anyLong(), any())).willThrow(
                ProjectNotExistException.class);

        mockMvc.perform(put("/api/members/{memberId}/projects/{projectId}", "user", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest("id", 1L, 1L))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProjectNotExistException));
    }

    @Test
    void updateMember_thenThrowAuthorityNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.updateMember(anyString(), anyLong(), any())).willThrow(
                AuthorityNotExistException.class);

        mockMvc.perform(put("/api/members/{memberId}/projects/{projectId}", "user", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest("id", 1L, 1L))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthorityNotExistException));
    }

    @Test
    void updateMember_thenThrowMemberNotExistException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        given(projectMemberService.updateMember(anyString(), anyLong(), any())).willThrow(
                MemberNotExistException.class);

        mockMvc.perform(put("/api/members/{memberId}/projects/{projectId}", "user", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProjectMemberRegisterRequest("id", 1L, 1L))))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MemberNotExistException));
    }
}
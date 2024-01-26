package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagModifyRequest;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskTagNotExistException;
import com.nhnacademy.minidooray.taskapi.service.TaskTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TaskTagRestControllerUnitTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TaskTagService taskTagService;

    @Test
    void createTaskTagSuccessTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(12L, 1L);
        given(taskTagService.createTaskTag(taskTagDto)).willReturn(taskTagDto);
        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", equalTo(12)))
                .andExpect(jsonPath("$.tagId", equalTo(1)));
    }

    @Test
    @DisplayName("해당 프로젝트에 없는 태그를 사용한 경우")
    void createTaskTagFailTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 12L);
        given(taskTagService.createTaskTag(taskTagDto)).willThrow(TaskNotExistException.class);
        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("업무에 동일한 태그를 사용한 경우")
    void createTaskTagFailTest2() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 1L);

        given(taskTagService.createTaskTag(taskTagDto)).willThrow(TaskNotExistException.class);
        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록되지 않는 업무에 태그를 사용한 경우")
    void createTaskTagFailTest3() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(20L, 1L);

        given(taskTagService.createTaskTag(taskTagDto)).willThrow(TaskNotExistException.class);
        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("업데이트 성공 테스트")
    void updateTaskTagByTagSuccessTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 2L);
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willReturn(taskTagDto);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 11, 1)
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", equalTo(11)))
                .andExpect(jsonPath("$.tagId", equalTo(2)));
    }

    @Test
    @DisplayName("프로젝트 태그에 없는 태그 아이디가 들어온 경우 테스트")
    void updateTaskTagByTagFailTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 3L);
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willThrow(TaskTagNotExistException.class);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 11, 1)
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("등록되지 않은 업무에 태그를 등록한 경우 테스트")
    void updateTaskTagByTagFailTest2() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(20L, 2L);
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willThrow(TaskTagNotExistException.class);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 20, 2L)
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("태그 삭제가 성공 테스트")
    void deleteTaskTagSuccessTest() throws Exception {
        doNothing().when(taskTagService).deleteTaskTag(11L, 1L);
        mockMvc.perform(delete("/api/tasks/{taskId}/tags/{targetTagId}", 11, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response", equalTo("OK")));
    }

    @Test
    @DisplayName("삭제하려는 태그에 업무가 없는 실패 테스트")
    @Transactional
    void deleteTaskTagFailTest() throws Exception {
        doThrow(new TaskNotExistException()).when(taskTagService).deleteTaskTag(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}/tags/{targetTagId}", 20, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("삭제하려는 태그가 없는 실패 테스트")
    void deleteTaskTagFailTest3() throws Exception {
        doThrow(new TagNotExistException()).when(taskTagService).deleteTaskTag(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}/tags/{targetTagId}", 11, 3))
                .andExpect(status().isBadRequest());
    }
}
package com.nhnacademy.minidooray.taskapi.controller;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagResponse;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskTagAlreadyExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskTagNotExistException;
import com.nhnacademy.minidooray.taskapi.service.TaskTagService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@WebMvcTest(TaskTagRestController.class)
class TaskTagRestControllerUnitTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TaskTagService taskTagService;

    @Test
    void getTaskTagByTaskIdTest() throws Exception {
        given(taskTagService.getTaskTagByTaskId(anyLong())).willReturn(
                List.of(new TaskTagResponse(1L, 1L, "task name")));

        mockMvc.perform(get("/api/tasks/{taskId}/tags", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].taskId", equalTo(1)))
                .andExpect(jsonPath("$[0].tagId", equalTo(1)))
                .andExpect(jsonPath("$[0].taskName", equalTo("task name")));
    }

    @Test
    void getTaskTagByTaskIdTest_thenThrowTaskNotExistException() throws Exception {
        given(taskTagService.getTaskTagByTaskId(anyLong())).willThrow(TaskNotExistException.class);

        mockMvc.perform(get("/api/tasks/{taskId}/tags", 1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    void createTaskTagSuccessTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(12L, 1L);

        given(taskTagService.createTaskTag(any())).willReturn(taskTagDto);

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
    void createTaskTagFailTest_thenThrowTagNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 12L);

        given(taskTagService.createTaskTag(any())).willThrow(TaskNotExistException.class);

        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    @DisplayName("업무에 동일한 태그를 사용한 경우")
    void createTaskTagFailTest_thenThrowTaskTagAlreadyExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 1L);

        given(taskTagService.createTaskTag(any())).willThrow(TaskTagAlreadyExistException.class);
        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskTagAlreadyExistException));
    }

    @Test
    @DisplayName("등록되지 않는 업무에 태그를 사용한 경우")
    void createTaskTagFailTest_thenThrowTaskNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(20L, 1L);

        given(taskTagService.createTaskTag(any())).willThrow(TaskNotExistException.class);
        mockMvc.perform(post("/api/tasks/tags")
                        .content(mapper.writeValueAsString(taskTagDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    @DisplayName("업데이트 성공 테스트")
    void updateTaskTagByTagSuccessTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TaskTagDto taskTagDto = new TaskTagDto(11L, 2L);
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willReturn(taskTagDto);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 11, 1)
                        .content(mapper.writeValueAsString(new TaskTagModifyRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", equalTo(11)))
                .andExpect(jsonPath("$.tagId", equalTo(2)));
    }

    @Test
    @DisplayName("task_tag에 올바른 task-tag 쌍이 부재한 경우")
    void updateTaskTagByTag_thenThrowTaskTagNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willThrow(TaskTagNotExistException.class);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 11, 1)
                        .content(mapper.writeValueAsString(new TaskTagModifyRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskTagNotExistException));
    }

    @Test
    @DisplayName("등록되지 않은 업무를 수정할 경우")
    void updateTaskTagByTagFailTest_thenThrowTaskNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willThrow(TaskNotExistException.class);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 20, 2L)
                        .content(mapper.writeValueAsString(new TaskTagModifyRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    @DisplayName("등록되지 않은 태그를 입력 또는 수정할 경우")
    void updateTaskTagByTagFailTest_thenThrowTagNotExistException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        given(taskTagService.updateTaskTagByTag(anyLong(), anyLong(), any())).willThrow(TagNotExistException.class);

        mockMvc.perform(put("/api/tasks/{taskId}/tags/{targetTagId}", 20, 2L)
                        .content(mapper.writeValueAsString(new TaskTagModifyRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TagNotExistException));
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
    void deleteTaskTagFailTest() throws Exception {
        doThrow(new TaskNotExistException()).when(taskTagService).deleteTaskTag(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}/tags/{targetTagId}", 20, 1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotExistException));
    }

    @Test
    @DisplayName("삭제하려는 태그가 없는 실패 테스트")
    void deleteTaskTagFailTest_thenThrowTagNotExistException() throws Exception {
        doThrow(new TagNotExistException()).when(taskTagService).deleteTaskTag(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}/tags/{targetTagId}", 11, 3))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TagNotExistException));
    }

    @Test
    void deleteTaskTag_thenThrowTaskTagNotExistException() throws Exception {
        doThrow(new TaskTagNotExistException()).when(taskTagService).deleteTaskTag(anyLong(), anyLong());

        mockMvc.perform(delete("/api/tasks/{taskId}/tags/{targetTagId}", 11, 3))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskTagNotExistException));
    }
}
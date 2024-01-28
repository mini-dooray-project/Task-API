package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskTagResponse;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import com.nhnacademy.minidooray.taskapi.exception.TagNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskTagAlreadyExistException;
import com.nhnacademy.minidooray.taskapi.repository.TagRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskTagServiceImplTest {

    @InjectMocks
    private TaskTagServiceImpl taskTagService;

    @Mock
    private TaskTagRepository taskTagRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TagRepository tagRepository;

    @Test
    void getTaskTagByTaskId() {
        Long taskId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(taskTagRepository.findByTaskTaskId(anyLong())).thenReturn(List.of(new TaskTagResponse()));

        List<TaskTagResponse> taskTagByTaskId = taskTagService.getTaskTagByTaskId(taskId);

        assertEquals(1, taskTagByTaskId.size());
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskTagRepository, times(1)).findByTaskTaskId(anyLong());
    }

    @Test
    void getTaskTagByTaskId_thenThrowTaskNotExistException() {
        Long taskId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TaskNotExistException.class, () -> taskTagService.getTaskTagByTaskId(taskId));
    }

    @Test
    void createTaskTag() {
        TaskTagDto taskTagDto = new TaskTagDto(1L, 1L);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(new Tag()));
        when(taskTagRepository.findById(any(TaskTag.Pk.class))).thenReturn(Optional.empty());

        TaskTagDto taskTag = taskTagService.createTaskTag(taskTagDto);

        assertNotNull(taskTag);
        assertEquals(taskTagDto.getTagId(), taskTag.getTagId());
        assertEquals(taskTagDto.getTaskId(), taskTag.getTaskId());
        verify(taskTagRepository, times(1)).save(any(TaskTag.class));
    }

    @Test
    void createTaskTag_TaskTagAlreadyExistException() {
        TaskTagDto taskTagDto = new TaskTagDto(1L, 1L);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(new Tag()));
        when(taskTagRepository.findById(any(TaskTag.Pk.class))).thenReturn(Optional.of(new TaskTag()));

        assertThrows(TaskTagAlreadyExistException.class, () -> taskTagService.createTaskTag(taskTagDto));
    }

    @Test
    void updateTaskTagByTag() {
        Long taskId = 1L;
        Long tagId = 1L;
        TaskTagModifyRequest modifyRequest = new TaskTagModifyRequest(1L);

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(modifyRequest.getTagId())).thenReturn(true);
        when(taskTagRepository.findById(any(TaskTag.Pk.class))).thenReturn(
                Optional.of(new TaskTag(new TaskTag.Pk(tagId, taskId), new Task(), new Tag())));

        TaskTagDto taskTagDto = taskTagService.updateTaskTagByTag(taskId, tagId, modifyRequest);

        assertNotNull(taskTagDto);
        assertEquals(taskTagDto.getTagId(), modifyRequest.getTagId());
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(tagRepository, times(2)).existsById(anyLong());
        verify(taskTagRepository, times(1)).findById(any());
    }

    @Test
    void updateTaskTagByTag_thenThrowTaskNotExistException() {
        Long taskId = 1L;
        Long tagId = 1L;
        TaskTagModifyRequest modifyRequest = new TaskTagModifyRequest(1L);

        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TaskNotExistException.class,
                () -> taskTagService.updateTaskTagByTag(taskId, tagId, modifyRequest));
    }

    @Test
    void updateTaskTagByTag_thenThrowTagNotExistException() {
        Long taskId = 1L;
        Long tagId = 1L;
        TaskTagModifyRequest modifyRequest = new TaskTagModifyRequest(1L);

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TagNotExistException.class,
                () -> taskTagService.updateTaskTagByTag(taskId, tagId, modifyRequest));
    }
    @Test
    void givenInvalidRequest_whenUpdateTaskTagByTag_thenThrowTagNotExistException() {
        Long taskId = 1L;
        Long tagId = 1L;
        TaskTagModifyRequest modifyRequest = new TaskTagModifyRequest(1L);

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(modifyRequest.getTagId())).thenReturn(false);

        assertThrows(TagNotExistException.class,
                () -> taskTagService.updateTaskTagByTag(taskId, tagId, modifyRequest));
    }

    @Test
    void deleteTaskTag() {
        Long taskId = 1L;
        Long tagId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(anyLong())).thenReturn(true);
        when(taskTagRepository.findById(any(TaskTag.Pk.class))).thenReturn(Optional.of(new TaskTag()));

        taskTagService.deleteTaskTag(taskId, tagId);

        verify(taskRepository, times(1)).existsById(anyLong());
        verify(tagRepository, times(1)).existsById(anyLong());
        verify(taskTagRepository, times(1)).delete(any(TaskTag.class));
    }

    @Test
    void deleteTaskTag_thenThrowTaskNotExistException() {
        Long taskId = 1L;
        Long tagId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TaskNotExistException.class, () -> taskTagService.deleteTaskTag(taskId, tagId));
    }

    @Test
    void deleteTaskTag_thenThrowTagNotExistException() {
        Long taskId = 1L;
        Long tagId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TagNotExistException.class, () -> taskTagService.deleteTaskTag(taskId, tagId));
    }
}
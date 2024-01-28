package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.TaskRequest;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponse;
import com.nhnacademy.minidooray.taskapi.domain.TaskResponseByProjectId;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.exception.MilestoneNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.TaskNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    void getTasks() {
        when(taskRepository.findAllBy()).thenReturn(List.of(new TaskResponse()));

        List<TaskResponse> tasks = taskService.getTasks();

        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAllBy();
    }

    @Test
    void getTaskByProjectId() {
        Long taskId =1L;

        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.findByProjectId(anyLong())).thenReturn(List.of(new TaskResponseByProjectId()));

        List<TaskResponseByProjectId> task = taskService.getTaskByProjectId(taskId);

        assertEquals(1, task.size());
        verify(projectRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).findByProjectId(anyLong());
    }

    @Test
    void getTaskByProjectId_thenThrowProjectNotExistException() {
        Long projectId = 1L;

        when(projectRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ProjectNotExistException.class, () -> taskService.getTaskByProjectId(projectId));
    }

    @Test
    void getTask() {
        Long taskId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.findByTaskId(anyLong())).thenReturn(new TaskResponse());

        TaskResponse task = taskService.getTask(taskId);

        assertNotNull(task);
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).findByTaskId(anyLong());
    }
    @Test
    void getTask_thenThrowTaskNotExistException() {
        Long taskId = 1L;

        when(taskRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(TaskNotExistException.class, () -> taskService.getTask(taskId));
    }

    @Test
    void createTask() {
        TaskRequest taskRequest = getTaskRequest();

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(new Milestone()));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));

        TaskResponse task = taskService.createTask(taskRequest);

        assertNotNull(task);
        verify(milestoneRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void createTask_thenThrowProjectNotExistException() {
        TaskRequest taskRequest = getTaskRequest();

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(new Milestone()));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProjectNotExistException.class, () -> taskService.createTask(taskRequest));
    }

    @Test
    void updateTask() {
        Long taskId = 1L;
        TaskRequest taskRequest = getTaskRequest();

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(new Milestone()));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));

        TaskResponse task = taskService.updateTask(taskId, taskRequest);

        assertNotNull(task);
        verify(taskRepository, times(1)).findById(anyLong());
        verify(milestoneRepository, times(2)).findById(anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateTask_thenThrowMilestoneNotExistException() {
        Long taskId = 1L;
        TaskRequest taskRequest = getTaskRequest();

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(MilestoneNotExistException.class, () -> taskService.updateTask(taskId, taskRequest));
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(new Task()));
        doNothing().when(taskRepository).delete(any(Task.class));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).delete(any(Task.class));
    }

    private TaskRequest getTaskRequest() {
        return new TaskRequest(1L, 1L, "title", "content", "account", LocalDateTime.now());
    }
}
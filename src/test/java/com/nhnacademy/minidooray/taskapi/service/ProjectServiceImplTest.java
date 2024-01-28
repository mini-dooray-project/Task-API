package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepository projectRepository;


    @Test
    void getProjects() {
        when(projectRepository.findAllBy()).thenReturn(List.of(new ProjectResponse()));

        List<ProjectResponse> responses = projectService.getProjects();

        verify(projectRepository, times(1)).findAllBy();
        assertNotNull(responses);
    }

    @Test
    void getProject_thenReturnProjectNotExistException() {
        Long projectId = 1L;

        when(projectRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ProjectNotExistException.class, () -> projectService.getProject(projectId));
        verify(projectRepository, times(1)).existsById(anyLong());
    }
}
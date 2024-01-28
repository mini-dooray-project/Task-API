package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneRequest;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.exception.MilestoneNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceImplTest {

    @InjectMocks
    private MilestoneServiceImpl milestoneService;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    void getMilestones() {
        when(milestoneRepository.findAllBy()).thenReturn(List.of(new MilestoneResponse()));

        List<MilestoneResponse> milestones = milestoneService.getMilestones();

        assertEquals(1, milestones.size());
        verify(milestoneRepository, times(1)).findAllBy();
    }

    @Test
    void getMilestone() {
        Long milestoneId = 1L;

        when(milestoneRepository.existsById(anyLong())).thenReturn(true);
        when(milestoneRepository.findByMilestoneId(anyLong())).thenReturn(new MilestoneResponse());

        MilestoneResponse milestone = milestoneService.getMilestone(milestoneId);

        assertNotNull(milestone);
        verify(milestoneRepository, times(1)).existsById(anyLong());
        verify(milestoneRepository, times(1)).findByMilestoneId(anyLong());
    }

    @Test
    void getMilestone_thenThrowMilestoneNotExistException() {
        Long milestoneId = 1L;

        when(milestoneRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(MilestoneNotExistException.class, () -> milestoneService.getMilestone(milestoneId));
    }

    @Test
    void getMilestoneByProjectId() {
        Long projectId = 1L;

        when(projectRepository.existsById(anyLong())).thenReturn(true);
        when(milestoneRepository.findByProjectId(anyLong())).thenReturn(List.of(new MilestoneResponse()));

        List<MilestoneResponse> milestone = milestoneService.getMilestoneByProjectId(projectId);

        assertEquals(1, milestone.size());
        verify(projectRepository, times(1)).existsById(anyLong());
        verify(milestoneRepository, times(1)).findByProjectId(anyLong());


    }
    @Test
    void getMilestoneByProjectId_thenThrowProjectNotExistException() {
        Long projectId = 1L;

        when(projectRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ProjectNotExistException.class, () -> milestoneService.getMilestoneByProjectId(projectId));
    }

    @Test
    void createMilestone() {
        MilestoneRequest milestoneRequest = new MilestoneRequest(1L, "name", LocalDateTime.now(), LocalDateTime.now());

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));

        MilestoneResponse milestone = milestoneService.createMilestone(milestoneRequest);

        assertNotNull(milestone);
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }

    @Test
    void updateMilestone() {
        Long milestoneId=  1L;
        MilestoneRequest milestoneRequest = new MilestoneRequest(1L, "name", LocalDateTime.now(), LocalDateTime.now());

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(new Milestone()));
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(new Project()));

        MilestoneResponse milestoneResponse = milestoneService.updateMilestone(milestoneId, milestoneRequest);

        assertNotNull(milestoneResponse);
        verify(milestoneRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteMilestone() {
        Long milestoneId=  1L;

        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(new Milestone()));
        doNothing().when(milestoneRepository).delete(any());

        milestoneService.deleteMilestone(milestoneId);

        verify(milestoneRepository, times(1)).findById(anyLong());
        verify(milestoneRepository, times(1)).delete(any(Milestone.class));
    }
}
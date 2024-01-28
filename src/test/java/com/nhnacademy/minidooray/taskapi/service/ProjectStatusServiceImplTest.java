package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.ProjectStatusResponse;
import com.nhnacademy.minidooray.taskapi.repository.ProjectStatusRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectStatusServiceImplTest {

    @InjectMocks
    private ProjectStatusServiceImpl projectStatusService;

    @Mock
    private ProjectStatusRepository projectStatusRepository;

    @Test
    void getProjectStatuses() {
        when(projectStatusRepository.findAllBy()).thenReturn(List.of(new ProjectStatusResponse() {
            @Override
            public Long getStatusId() {
                return 1L;
            }

            @Override
            public String getStatusName() {
                return "name";
            }
        }));

        List<ProjectStatusResponse> projectStatuses = projectStatusService.getProjectStatuses();

        assertEquals(1, projectStatuses.size());
        verify(projectStatusRepository, times(1)).findAllBy();
    }
}
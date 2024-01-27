package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectStatusResponse;
import com.nhnacademy.minidooray.taskapi.repository.ProjectStatusRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService{

    private final ProjectStatusRepository projectStatusRepository;

    public ProjectStatusServiceImpl(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    @Override
    public List<ProjectStatusResponse> getProjectStatuses() {
        return projectStatusRepository.findAllBy();
    }
}

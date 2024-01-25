package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import java.util.List;

public interface ProjectService {

    List<ProjectDto> getProjects();
    ProjectDto getProject(Long projectId);

    ProjectDto createProject(ProjectRequest projectRequest);

    ProjectDto updateProject(Long productId, ProjectRequest projectRequest);

    void deleteProject(Long productId);
}

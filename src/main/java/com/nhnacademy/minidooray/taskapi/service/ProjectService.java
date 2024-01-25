package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import java.util.List;

public interface ProjectService {

    List<ProjectResponse> getProjects();
    ProjectResponse getProject(Long projectId);

    ProjectResponse createProject(ProjectRequest projectRequest);

    ProjectResponse updateProject(Long productId, ProjectRequest projectRequest);

    void deleteProject(Long productId);
}

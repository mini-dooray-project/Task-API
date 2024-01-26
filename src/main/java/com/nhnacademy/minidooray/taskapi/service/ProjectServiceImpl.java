package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectStatus;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectStatusNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectStatusRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectStatusRepository statusRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectStatusRepository statusRepository) {
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public List<ProjectResponse> getProjects() {
        return projectRepository.findAllBy();
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotExistException();
        }
        return projectRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        ProjectStatus projectStatus = statusRepository.findById(projectRequest.getStatusId())
                .orElseThrow(ProjectStatusNotExistException::new);

        Project project = new Project(projectStatus, projectRequest.getProjectName());
        projectRepository.save(project);
        return new ProjectResponse().entityToDto(project);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long productId, ProjectRequest projectRequest) {
        ProjectStatus projectStatus = statusRepository.findById(projectRequest.getStatusId())
                .orElseThrow(ProjectStatusNotExistException::new);

        Project project = projectRepository.findById(productId)
                .orElseThrow(ProjectNotExistException::new);

        Project updateProject = project.updateProject(projectRequest.getProjectName(), projectStatus);

        return new ProjectResponse().entityToDto(updateProject);
    }

    @Override
    @Transactional
    public void deleteProject(Long productId) {
        Project project = projectRepository.findById(productId)
                .orElseThrow(ProjectNotExistException::new);

        projectRepository.delete(project);
    }
}

package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import com.nhnacademy.minidooray.taskapi.entity.Project;
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
        if (Objects.isNull(projectRepository.findByProjectId(projectId))) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }
        return projectRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        if (statusRepository.findById(projectRequest.getStatusId()).isEmpty()) {
            throw new ProjectStatusNotExistException("프로젝트 상태가 존재하지 않습니다.");
        }

        Project project = new Project(statusRepository.findById(projectRequest.getStatusId()).get(),
                projectRequest.getProjectName());
        projectRepository.save(project);
        return new ProjectResponse().entityToDto(project);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long productId, ProjectRequest projectRequest) {
        if (statusRepository.findById(projectRequest.getStatusId()).isEmpty()) {
            throw new ProjectStatusNotExistException("프로젝트 상태가 존재하지 않습니다.");
        }

        Optional<Project> byId = projectRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }

        Project project = byId.get().updateProject(projectRequest.getProjectName(),
                statusRepository.findById(projectRequest.getStatusId()).get());

        return new ProjectResponse().entityToDto(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long productId) {
        Optional<Project> byId = projectRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }
        projectRepository.deleteById(productId);
    }
}

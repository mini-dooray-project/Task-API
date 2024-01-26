package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.DeleteResponse;
import com.nhnacademy.minidooray.taskapi.domain.ProjectResponse;
import com.nhnacademy.minidooray.taskapi.domain.ProjectRequest;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    private final ProjectService projectService;

    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/{projectId}")
    public ProjectResponse getProject(@PathVariable Long projectId) {
        return projectService.getProject(projectId);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest projectRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        ProjectResponse project = projectService.createProject(projectRequest);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long projectId,
                                                         @Valid @RequestBody ProjectRequest projectRequest,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        ProjectResponse project = projectService.updateProject(projectId, projectRequest);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public DeleteResponse deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new DeleteResponse("OK");
    }
}

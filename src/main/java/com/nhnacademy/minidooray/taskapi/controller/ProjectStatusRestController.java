package com.nhnacademy.minidooray.taskapi.controller;


import com.nhnacademy.minidooray.taskapi.domain.ProjectStatusResponse;
import com.nhnacademy.minidooray.taskapi.service.ProjectStatusService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class ProjectStatusRestController {
    private final ProjectStatusService projectStatusService;

    public ProjectStatusRestController(ProjectStatusService projectStatusService) {
        this.projectStatusService = projectStatusService;
    }

    @GetMapping
    public List<ProjectStatusResponse> getStatuses() {
        return projectStatusService.getProjectStatuses();
    }
}

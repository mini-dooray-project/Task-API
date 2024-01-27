package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Project;
import lombok.Getter;

@Getter
public class ProjectResponse {
    private Long projectId;
    private Long statusId;
    private String statusName;
    private String projectName;

    public ProjectResponse entityToDto(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.statusId = project.getProjectStatus().getStatusId();
        this.statusName = project.getProjectStatus().getStatusName();

        return this;
    }

    public ProjectResponse(Long projectId, Long statusId, String statusName, String projectName) {
        this.projectId = projectId;
        this.statusId = statusId;
        this.statusName = statusName;
        this.projectName = projectName;
    }

    public ProjectResponse() {
    }
}

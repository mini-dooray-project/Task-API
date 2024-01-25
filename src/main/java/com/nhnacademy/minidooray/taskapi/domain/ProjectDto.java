package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Project;
import lombok.Getter;


@Getter
public class ProjectDto {
    private Long projectId;
    private Long statusId;
    private String projectName;

    public ProjectDto entityToDto(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.statusId = project.getProjectStatus().getStatusId();

        return this;
    }

    public ProjectDto(Long projectId, Long statusId, String projectName) {
        this.projectId = projectId;
        this.statusId = statusId;
        this.projectName = projectName;
    }

    public ProjectDto() {
    }
}

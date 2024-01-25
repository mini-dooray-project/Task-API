package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Project;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProjectResponse {
    private Long projectId;
    private Long statusId;
    private String projectName;

    public ProjectResponse entityToDto(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.statusId = project.getProjectStatus().getStatusId();

        return this;
    }

    public ProjectResponse(Long projectId, Long statusId, String projectName) {
        this.projectId = projectId;
        this.statusId = statusId;
        this.projectName = projectName;
    }

    public ProjectResponse() {
    }
}

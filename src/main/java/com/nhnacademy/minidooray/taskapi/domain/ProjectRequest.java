package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Data;

@Data
public class ProjectRequest {
    private Long statusId;
    private String projectName;

    public ProjectRequest() {
    }

    public ProjectRequest(Long statusId, String projectName) {
        this.statusId = statusId;
        this.projectName = projectName;
    }
}

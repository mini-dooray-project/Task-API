package com.nhnacademy.minidooray.taskapi.domain;


import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequest {
    private Long statusId;
    @NotBlank
    private String projectName;

    public ProjectRequest() {
    }

    public ProjectRequest(Long statusId, String projectName) {
        this.statusId = statusId;
        this.projectName = projectName;
    }
}

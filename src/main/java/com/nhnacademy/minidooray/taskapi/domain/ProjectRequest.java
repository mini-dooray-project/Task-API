package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class ProjectRequest {
    private Long statusId;
    private String projectName;

    public ProjectRequest() {
    }
}

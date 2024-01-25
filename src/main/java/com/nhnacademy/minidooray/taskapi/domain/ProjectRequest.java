package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    private Long statusId;
    private String projectName;

    public ProjectRequest() {
    }
}

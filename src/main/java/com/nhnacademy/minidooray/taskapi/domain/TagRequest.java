package com.nhnacademy.minidooray.taskapi.domain;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TagRequest {
    Long projectId;
    @NotBlank
    String tagName;

    public TagRequest() {
    }

    public TagRequest(Long projectId, String tagName) {
        this.projectId = projectId;
        this.tagName = tagName;
    }
}

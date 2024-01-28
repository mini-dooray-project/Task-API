package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class TaskTagResponse {
    Long taskId;
    Long tagId;
    String taskName;

    public TaskTagResponse(Long taskId, Long tagId, String taskName) {
        this.taskId = taskId;
        this.tagId = tagId;
        this.taskName = taskName;
    }

    public TaskTagResponse() {
    }
}

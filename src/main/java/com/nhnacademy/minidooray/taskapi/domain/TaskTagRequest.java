package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskTagRequest {
    private Long taskId;
    private Long tagId;

    public TaskTagRequest() {
    }

    public TaskTagRequest entityToDto(TaskTag tagTag) {
        this.tagId = tagTag.getPk().getTagId();
        this.taskId = tagTag.getTask().getTaskId();
        return this;
    }
}

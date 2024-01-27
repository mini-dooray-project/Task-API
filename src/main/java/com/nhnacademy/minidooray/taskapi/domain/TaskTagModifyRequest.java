package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import lombok.Getter;

@Getter
public class TaskTagModifyRequest {
    private Long tagId;

    public TaskTagModifyRequest entityToDto(TaskTag tagTag) {
        this.tagId = tagTag.getPk().getTagId();

        return this;
    }
}

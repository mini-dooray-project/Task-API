package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import lombok.Data;

@Data
public class TaskTagDto {
    private Long taskId;
    private Long tagId;

    public TaskTagDto() {
    }

    public TaskTagDto(Long taskId, Long tagId) {
        this.taskId = taskId;
        this.tagId = tagId;
    }

    public TaskTagDto entityToDto(TaskTag tagTag) {
        this.tagId = tagTag.getPk().getTagId();
        this.taskId = tagTag.getTask().getTaskId();
        return this;
    }
}

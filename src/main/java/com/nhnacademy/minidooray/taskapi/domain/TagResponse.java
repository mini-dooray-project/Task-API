package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Tag;
import lombok.Getter;

@Getter
public class TagResponse {
    Long tagId;
    Long projectId;
    String tagName;

    public TagResponse(Long tagId, Long projectId, String tagName) {
        this.tagId = tagId;
        this.projectId = projectId;
        this.tagName = tagName;
    }

    public TagResponse() {
    }

    public TagResponse entityToDto(Tag tag) {
        this.tagId = tag.getTagId();
        this.projectId = tag.getProject().getProjectId();
        this.tagName = tag.getTagName();

        return this;
    }
}

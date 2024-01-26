package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class ProjectMemberModifyRequest {

    private Long authorityId;

    public ProjectMemberModifyRequest(Long authorityId) {
        this.authorityId = authorityId;
    }

    public ProjectMemberModifyRequest() {
    }
}

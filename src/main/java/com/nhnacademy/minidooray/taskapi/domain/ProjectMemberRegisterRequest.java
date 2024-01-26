package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class ProjectMemberRegisterRequest {
    String memberId;
    Long projectId;
    Long authorityId;


    public ProjectMemberRegisterRequest(String memberId, Long projectId, Long authorityId) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.authorityId = authorityId;
    }

    public ProjectMemberRegisterRequest() {
    }
}

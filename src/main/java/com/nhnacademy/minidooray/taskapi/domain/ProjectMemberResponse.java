package com.nhnacademy.minidooray.taskapi.domain;


import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import lombok.Getter;

@Getter
public class ProjectMemberResponse {
    String memberId;
    Long projectId;
    String projectName;
    Long authorityId;
    String authorityName;


    public ProjectMemberResponse(String memberId, Long projectId, String projectName, Long authorityId,
                                 String authorityName) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.projectName = projectName;
        this.authorityId = authorityId;
        this.authorityName = authorityName;
    }

    public ProjectMemberResponse() {
    }

    public ProjectMemberResponse entityToDto(ProjectMember projectMember) {
        this.memberId = projectMember.getPk().getMemberId();
        this.projectId = projectMember.getPk().getProjectId();
        this.projectName = projectMember.getProject().getProjectName();
        this.authorityId = projectMember.getProjectAuthority().getAuthorityId();
        this.authorityName = projectMember.getProjectAuthority().getAuthorityName();

        return this;
    }
}

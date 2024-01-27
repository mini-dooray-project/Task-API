package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberResponse> getMembersByProject(Long projectId);

    List<ProjectMemberResponse> getMemberByMemberId(String memberId);

    ProjectMemberResponse createMember(ProjectMemberRegisterRequest memberRequest);

    ProjectMemberResponse updateMember(String memberId, Long projectId, ProjectMemberModifyRequest memberRequest);

}

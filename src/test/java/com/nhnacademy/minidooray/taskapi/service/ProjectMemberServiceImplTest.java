package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import com.nhnacademy.minidooray.taskapi.exception.MemberNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProjectMemberServiceImplTest {

    @InjectMocks
    private ProjectMemberServiceImpl projectMemberService;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Test
    void getMembersByProject() {
        // given
        Long projectId = 10L;

        // mocking
        given(projectMemberRepository.existsByPk_projectId(anyLong()))
                .willReturn(true);
        given(projectMemberRepository.findByPk_projectId(anyLong()))
                .willReturn(List.of(createMemberResponse()));

        // when
        List<ProjectMemberResponse> responses = projectMemberService.getMembersByProject(projectId);

        // then
        List<ProjectMemberResponse> byPkProjectId = projectMemberRepository.findByPk_projectId(projectId);

        assertEquals(responses.get(0).getMemberId(), byPkProjectId.get(0).getMemberId());
        assertEquals(responses.get(0).getProjectId(), byPkProjectId.get(0).getProjectId());
        assertEquals(responses.get(0).getProjectName(), byPkProjectId.get(0).getProjectName());
        assertEquals(responses.get(0).getAuthorityId(), byPkProjectId.get(0).getAuthorityId());
        assertEquals(responses.get(0).getAuthorityName(), byPkProjectId.get(0).getAuthorityName());
    }

    @Test
    void getMembersByProjectId_thenThrowProjectNotExistException() throws Exception {
        // given
        Long projectId = 1L;

        // mocking
        given(projectMemberRepository.existsByPk_projectId(anyLong())).willReturn(false);

        assertThrows(ProjectNotExistException.class, () -> projectMemberService.getMembersByProject(projectId));
    }
    @Test
    void getMemberByMemberId() {
        String memberId = "user";

        given(projectMemberRepository.existsByPk_memberId(anyString())).willReturn(true);
        given(projectMemberRepository.findByPk_memberId(anyString())).willReturn(List.of(createMemberResponse()));

        List<ProjectMemberResponse> memberByMemberId = projectMemberService.getMemberByMemberId(memberId);
        List<ProjectMemberResponse> byPkMemberId = projectMemberRepository.findByPk_memberId(memberId);

        assertEquals(memberByMemberId.get(0).getMemberId(), byPkMemberId.get(0).getMemberId());
        assertEquals(memberByMemberId.get(0).getProjectId(), byPkMemberId.get(0).getProjectId());
        assertEquals(memberByMemberId.get(0).getProjectName(), byPkMemberId.get(0).getProjectName());
        assertEquals(memberByMemberId.get(0).getAuthorityId(), byPkMemberId.get(0).getAuthorityId());
        assertEquals(memberByMemberId.get(0).getAuthorityName(), byPkMemberId.get(0).getAuthorityName());
    }

    @Test
    void getMembersByMemberId_thenThrowMemberNotExistException() throws Exception {
        // given
        String memberId = "user";

        // mocking
        given(projectMemberRepository.existsByPk_memberId(anyString())).willReturn(false);

        assertThrows(MemberNotExistException.class, () -> projectMemberService.getMemberByMemberId(memberId));
    }

    @Test
    void createMember() {
        ProjectMemberRegisterRequest memberRegisterRequest = new ProjectMemberRegisterRequest("user", 1L, 1L);
//        ReflectionTestUtils.setField(memberRegisterRequest, "memberRequest", );
    }

    @Test
    void updateMember() {
    }

    private ProjectMemberResponse createMemberResponse() {
        return new ProjectMemberResponse("user", 1L, "name", 1L, "activate");
    }
}
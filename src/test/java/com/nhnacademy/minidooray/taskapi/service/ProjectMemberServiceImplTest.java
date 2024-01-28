package com.nhnacademy.minidooray.taskapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectAuthority;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.exception.MemberNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectAuthorityRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectMemberServiceImplTest {

    @InjectMocks
    private ProjectMemberServiceImpl projectMemberService;

    @Mock
    private ProjectMemberRepository projectMemberRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectAuthorityRepository authorityRepository;

    @Test
    void getMembersByProject() {
        // given
        Long projectId = 10L;
        ProjectMemberResponse memberResponse = createMemberResponse();

        // mocking
        given(projectMemberRepository.existsByPk_projectId(anyLong()))
                .willReturn(true);
        given(projectMemberRepository.findByPkProjectId(anyLong()))
                .willReturn(List.of(createMemberResponse()));

        // when
        List<ProjectMemberResponse> responses = projectMemberService.getMembersByProject(projectId);

        // then
        assertNotNull(responses);
        assertEquals(responses.get(0).getMemberId(), memberResponse.getMemberId());
        assertEquals(responses.get(0).getProjectId(), memberResponse.getProjectId());
        assertEquals(responses.get(0).getProjectName(), memberResponse.getProjectName());
        assertEquals(responses.get(0).getAuthorityId(), memberResponse.getAuthorityId());
        assertEquals(responses.get(0).getAuthorityName(), memberResponse.getAuthorityName());

        verify(projectMemberRepository, times(1)).findByPkProjectId(projectId);
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
        given(projectMemberRepository.findByPkMemberId(anyString())).willReturn(List.of(createMemberResponse()));

        List<ProjectMemberResponse> memberByMemberId = projectMemberService.getMemberByMemberId(memberId);
        List<ProjectMemberResponse> byPkMemberId = projectMemberRepository.findByPkMemberId(memberId);

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
    void getProjectMemberAuthority() {
        String memberId = "user";
        Long projectId = 1L;

        when(projectMemberRepository.findById(any())).thenReturn(Optional.of(new ProjectMember()));

        boolean authority = projectMemberService.getProjectMemberAuthority(memberId, projectId);

        assertEquals(true, authority);
        verify(projectMemberRepository, times(1)).findById(any());
    }

    @Test
    void createMember() {
        ProjectMemberRegisterRequest memberRegisterRequest = new ProjectMemberRegisterRequest("user", 1L, 1L);

        given(projectRepository.findById(anyLong())).willReturn(Optional.of(new Project()));
        given(authorityRepository.findById(anyLong())).willReturn(Optional.of(new ProjectAuthority()));

        ProjectMemberResponse memberResponse = projectMemberService.createMember(memberRegisterRequest);

        assertNotNull(memberResponse);
        verify(projectMemberRepository, times(1)).save(any(ProjectMember.class));
    }

    @Test
    void updateMember() {
        String memberId = "user";
        Long projectId = 1L;
        ProjectMemberModifyRequest modifyRequest = new ProjectMemberModifyRequest(1L);

        given(projectRepository.existsById(projectId)).willReturn(true);
        when(authorityRepository.findById(modifyRequest.getAuthorityId())).thenReturn(
                Optional.of(new ProjectAuthority(1L, "name")));
        when(projectMemberRepository.findById(any())).thenReturn(
                Optional.of(new ProjectMember(new ProjectMember.Pk(memberId, projectId), new Project(),
                        new ProjectAuthority())));

        ProjectMemberResponse response = projectMemberService.updateMember(memberId, projectId, modifyRequest);

        assertNotNull(response);
        verify(projectRepository, times(1)).existsById(anyLong());
        verify(authorityRepository, times(1)).findById(anyLong());
        verify(projectMemberRepository, times(1)).findById(any());
    }

    @Test
    void updateMember_thenThrowProjectNotExistException() throws Exception {
        String memberId = "user";
        Long projectId = 1L;
        ProjectMemberModifyRequest modifyRequest = new ProjectMemberModifyRequest();

        when(projectRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ProjectNotExistException.class,
                () -> projectMemberService.updateMember(memberId, projectId, modifyRequest));
    }

    private ProjectMemberResponse createMemberResponse() {
        return new ProjectMemberResponse("user", 1L, "name", 1L, "activate");
    }
}
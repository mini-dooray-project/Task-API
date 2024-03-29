package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectAuthority;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.exception.AuthorityNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.MemberNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectAuthorityRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final ProjectAuthorityRepository authorityRepository;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository,
                                    ProjectRepository projectRepository,
                                    ProjectAuthorityRepository authorityRepository) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectRepository = projectRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<ProjectMemberResponse> getMembersByProject(Long projectId) {
        if (!projectMemberRepository.existsByPk_projectId(projectId)) {
            throw new ProjectNotExistException();
        }
        return projectMemberRepository.findByPkProjectId(projectId);
    }

    @Override
    public List<ProjectMemberResponse> getMemberByMemberId(String memberId) {
        if (!projectMemberRepository.existsByPk_memberId(memberId)) {
            throw new MemberNotExistException();
        }
        return projectMemberRepository.findByPkMemberId(memberId);
    }

    @Override
    @Transactional
    public ProjectMemberResponse createMember(ProjectMemberRegisterRequest memberRequest) {
        Project project = projectRepository.findById(memberRequest.getProjectId())
                .orElseThrow(ProjectNotExistException::new);
        ProjectAuthority authority = authorityRepository.findById(memberRequest.getAuthorityId())
                .orElseThrow(AuthorityNotExistException::new);

        ProjectMember projectMember = new ProjectMember(new ProjectMember.Pk(memberRequest.getMemberId(),
                memberRequest.getProjectId()), project, authority);
        projectMemberRepository.save(projectMember);

        return new ProjectMemberResponse().entityToDto(projectMember);
    }

    @Override
    @Transactional
    public ProjectMemberResponse updateMember(String memberId, Long projectId,
                                              ProjectMemberModifyRequest memberRequest) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotExistException();
        }

        ProjectAuthority authority = authorityRepository.findById(memberRequest.getAuthorityId())
                .orElseThrow(AuthorityNotExistException::new);
        ProjectMember projectMember =
                projectMemberRepository.findById(new ProjectMember.Pk(memberId, projectId)).orElseThrow(
                        MemberNotExistException::new);

        ProjectMember updatedProjectMember = projectMember.updateProjectMember(authority);
        return new ProjectMemberResponse().entityToDto(updatedProjectMember);
    }

    @Override
    public boolean getProjectMemberAuthority(String memberId, Long projectId) {
        Optional<ProjectMember> member = projectMemberRepository.findById(new ProjectMember.Pk(memberId, projectId));
        return member.isEmpty() ? false : true;
    }

}

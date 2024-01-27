package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMember.Pk> {
    @Query("select new com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse(p.pk.memberId, p.project.projectId, p.project.projectName, p.projectAuthority.authorityId, p.projectAuthority.authorityName) from ProjectMember p where p.pk.projectId=:projectId")
    List<ProjectMemberResponse> findByPk_projectId(@Param("projectId") Long projectId);

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.ProjectMemberResponse(p.pk.memberId, p.project.projectId, p.project.projectName, p.projectAuthority.authorityId, p.projectAuthority.authorityName) from ProjectMember p where p.pk.memberId=:memberId")
    List<ProjectMemberResponse> findByPk_memberId(@Param("memberId") String memberId);

    boolean existsByPk_memberId(String memberId);
    boolean existsByPk_projectId(Long projectId);
}

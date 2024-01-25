package com.nhnacademy.minidooray.taskapi.controller.repository;

import com.nhnacademy.minidooray.taskapi.controller.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMember.Pk> {
}

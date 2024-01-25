package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select new com.nhnacademy.minidooray.taskapi.domain.ProjectDto(p.projectId, p.projectStatus.statusId, p.projectName) from Project p")
    List<ProjectDto> findAllBy();

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.ProjectDto(p.projectId, p.projectStatus.statusId, p.projectName) from Project p where p.projectId=:projectId")
    ProjectDto findByProjectId(@Param("projectId") Long projectId);
}

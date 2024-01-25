package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
}

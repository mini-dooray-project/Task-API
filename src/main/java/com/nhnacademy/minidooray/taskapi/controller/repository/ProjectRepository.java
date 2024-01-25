package com.nhnacademy.minidooray.taskapi.controller.repository;

import com.nhnacademy.minidooray.taskapi.controller.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}

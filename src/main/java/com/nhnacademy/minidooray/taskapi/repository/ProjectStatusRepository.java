package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectStatusResponse;
import com.nhnacademy.minidooray.taskapi.entity.ProjectStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    List<ProjectStatusResponse> findAllBy();
}

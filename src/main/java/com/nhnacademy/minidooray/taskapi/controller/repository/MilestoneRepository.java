package com.nhnacademy.minidooray.taskapi.controller.repository;

import com.nhnacademy.minidooray.taskapi.controller.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}

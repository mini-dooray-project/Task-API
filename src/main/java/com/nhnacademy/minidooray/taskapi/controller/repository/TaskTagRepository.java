package com.nhnacademy.minidooray.taskapi.controller.repository;

import com.nhnacademy.minidooray.taskapi.controller.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {
}

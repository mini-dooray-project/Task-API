package com.nhnacademy.minidooray.taskapi.controller.repository;

import com.nhnacademy.minidooray.taskapi.controller.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

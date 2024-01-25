package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.TaskDto(t.taskId, t.title, t.content, t.registrantAccount, t.createdDate, t.expireDate, t.milestone.milestoneId, t.project.projectId) from Task t")
    List<TaskDto> findAllBy();
    @Query("select new com.nhnacademy.minidooray.taskapi.domain.TaskDto(t.taskId, t.title, t.content, t.registrantAccount, t.createdDate, t.expireDate, t.milestone.milestoneId, t.project.projectId) from Task t where t.taskId=:taskId")
    TaskDto findByTaskId(@Param("taskId") Long taskId);
}

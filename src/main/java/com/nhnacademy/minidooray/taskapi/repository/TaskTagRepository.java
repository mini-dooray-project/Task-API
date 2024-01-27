package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagResponse;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.TaskTagResponse(t.pk.taskId, t.pk.tagId, t.tag.tagName) from TaskTag t where t.pk.taskId=:taskId")
    List<TaskTagResponse> findByTask_taskId(@Param("taskId") Long taskId);
}

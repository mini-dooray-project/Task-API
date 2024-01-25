package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.CommentResponse;
import com.nhnacademy.minidooray.taskapi.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select new com.nhnacademy.minidooray.taskapi.domain.CommentResponse(c.commentId, c.task.taskId, c.registrantAccount, c.createdDate, c.content) from Comment c where c.task.taskId = :taskId")
    List<CommentResponse> findByTask_TaskId(@Param("taskId") Long taskId);
}

package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.TagResponse;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select new com.nhnacademy.minidooray.taskapi.domain.TagResponse(t.tagId, t.project.projectId, t.tagName) from Tag t")
    List<TagResponse> findAllBy();

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.TagResponse(t.tagId, t.project.projectId, t.tagName) from Tag t where t.tagId=:tagId")
    TagResponse findByTagId(@Param("tagId") Long tagId);

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.TagResponse(t.tagId, t.project.projectId, t.tagName) from Tag t where t.project.projectId=:projectId")
    List<TagResponse> findByProjectId(@Param("projectId") Long projectId);

}

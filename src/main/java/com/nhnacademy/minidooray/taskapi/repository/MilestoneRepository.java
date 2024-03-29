package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    @Query("select new com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse(m.milestoneId, m.project.projectId, m.milestoneName, m.startDate, m.milestoneExpireDate) from Milestone m")
    List<MilestoneResponse> findAllBy();

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse(m.milestoneId, m.project.projectId, m.milestoneName, m.startDate, m.milestoneExpireDate) from Milestone m where m.milestoneId=:milestoneId")
    MilestoneResponse findByMilestoneId(@Param("milestoneId") Long milestoneId);

    @Query("select new com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse(m.milestoneId, m.project.projectId, m.milestoneName, m.startDate, m.milestoneExpireDate) from Milestone m where m.project.projectId=:projectId")
    List<MilestoneResponse> findByProjectId(@Param("projectId") Long projectId);

}

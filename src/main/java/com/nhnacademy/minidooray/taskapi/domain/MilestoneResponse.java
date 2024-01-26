package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MilestoneResponse {
    private Long milestoneId;
    private Long projectId;
    private String milestoneName;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;

    public MilestoneResponse entityToDto(Milestone milestone) {
        this.milestoneId = milestone.getMilestoneId();
        this.projectId = milestone.getProject().getProjectId();
        this.milestoneName = milestone.getMilestoneName();
        this.startDate = milestone.getStartDate();
        this.expireDate = milestone.getExpireDate();

        return this;
    }

    public MilestoneResponse(Long milestoneId, Long projectId, String milestoneName, LocalDateTime startDate,
                             LocalDateTime expireDate) {
        this.milestoneId = milestoneId;
        this.projectId = projectId;
        this.milestoneName = milestoneName;
        this.startDate = startDate;
        this.expireDate = expireDate;
    }

    public MilestoneResponse() {
    }
}

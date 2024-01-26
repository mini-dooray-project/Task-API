package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneRequest;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse;
import java.util.List;

public interface MilestoneService {
    List<MilestoneResponse> getMilestones();
    MilestoneResponse getMilestone(Long milestoneId);
    MilestoneResponse createMilestone(MilestoneRequest request);

    MilestoneResponse updateMilestone(Long milestoneId, MilestoneRequest milestoneRequest);

    void deleteMilestone(Long milestoneId);
}

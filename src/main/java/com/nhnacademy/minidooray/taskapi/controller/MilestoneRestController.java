package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.DeleteResponse;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneRequest;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse;
import com.nhnacademy.minidooray.taskapi.service.MilestoneService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneRestController {

    private final MilestoneService milestoneService;

    public MilestoneRestController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public List<MilestoneResponse> getMilestones() {
        return milestoneService.getMilestones();
    }

    @GetMapping("/{milestoneId}")
    public MilestoneResponse getMilestone(@PathVariable Long milestoneId) {
        return milestoneService.getMilestone(milestoneId);
    }

    @PostMapping
    public MilestoneResponse createMilestone(@RequestBody MilestoneRequest milestoneRequest) {
        return milestoneService.createMilestone(milestoneRequest);
    }

    @PutMapping("/{milestoneId}")
    public MilestoneResponse updateMilestone(@PathVariable Long milestoneId,
                                             @RequestBody MilestoneRequest milestoneRequest) {
        return milestoneService.updateMilestone(milestoneId, milestoneRequest);
    }

    @DeleteMapping("/{milestoneId}")
    public DeleteResponse deleteMilestone(@PathVariable Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);
        return new DeleteResponse("OK");
    }
}

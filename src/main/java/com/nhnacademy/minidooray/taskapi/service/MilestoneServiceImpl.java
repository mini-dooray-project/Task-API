package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneRequest;
import com.nhnacademy.minidooray.taskapi.domain.MilestoneResponse;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.exception.MilestoneNotExistException;
import com.nhnacademy.minidooray.taskapi.exception.ProjectNotExistException;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public MilestoneServiceImpl(MilestoneRepository milestoneRepository, ProjectRepository projectRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<MilestoneResponse> getMilestones() {
        return milestoneRepository.findAllBy();
    }

    @Override
    public MilestoneResponse getMilestone(Long milestoneId) {
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new MilestoneNotExistException("마일스톤이 존재하지 않습니다.");
        }
        return milestoneRepository.findByProjectId(milestoneId);
    }

    @Override
    @Transactional
    public MilestoneResponse createMilestone(MilestoneRequest milestoneRequest) {
        Optional<Project> project = projectRepository.findById(milestoneRequest.getProjectId());
        if (project.isEmpty()) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }
        Milestone milestone =
                new Milestone(project.get(), milestoneRequest.getMilestoneName(), milestoneRequest.getStartDate(),
                        milestoneRequest.getExpireDate());
        milestoneRepository.save(milestone);
        return new MilestoneResponse().entityToDto(milestone);
    }

    @Override
    @Transactional
    public MilestoneResponse updateMilestone(Long milestoneId, MilestoneRequest milestoneRequest) {
        Milestone prevMilestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new MilestoneNotExistException("기존의 마일스톤이 존재하지 않습니다."));
        Optional<Project> project = projectRepository.findById(milestoneRequest.getProjectId());

        if (project.isEmpty()) {
            throw new ProjectNotExistException("프로젝트가 존재하지 않습니다.");
        }

        Milestone updatedMilestone = prevMilestone.updateMilestone(project.get(), milestoneRequest.getMilestoneName(),
                milestoneRequest.getStartDate(), milestoneRequest.getExpireDate());

        return new MilestoneResponse().entityToDto(updatedMilestone);
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }
}

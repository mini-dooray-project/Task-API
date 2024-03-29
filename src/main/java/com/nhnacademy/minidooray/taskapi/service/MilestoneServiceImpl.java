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
            throw new MilestoneNotExistException();
        }
        return milestoneRepository.findByMilestoneId(milestoneId);
    }

    @Override
    public List<MilestoneResponse> getMilestoneByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotExistException();
        }

        return milestoneRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public MilestoneResponse createMilestone(MilestoneRequest milestoneRequest) {
        Project project =
                projectRepository.findById(milestoneRequest.getProjectId()).orElseThrow(ProjectNotExistException::new);
        Milestone milestone =
                new Milestone(project, milestoneRequest.getMilestoneName(), milestoneRequest.getStartDate(),
                        milestoneRequest.getMilestoneExpireDate());
        milestoneRepository.save(milestone);
        return new MilestoneResponse().entityToDto(milestone);
    }

    @Override
    @Transactional
    public MilestoneResponse updateMilestone(Long milestoneId, MilestoneRequest milestoneRequest) {
        Milestone prevMilestone =
                milestoneRepository.findById(milestoneId).orElseThrow(MilestoneNotExistException::new);
        Project project =
                projectRepository.findById(milestoneRequest.getProjectId()).orElseThrow(ProjectNotExistException::new);

        Milestone updatedMilestone = prevMilestone.updateMilestone(project, milestoneRequest.getMilestoneName(),
                milestoneRequest.getStartDate(), milestoneRequest.getMilestoneExpireDate());

        return new MilestoneResponse().entityToDto(updatedMilestone);
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(MilestoneNotExistException::new);
        milestoneRepository.delete(milestone);
    }
}

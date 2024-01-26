package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Task;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class TaskResponse {
    // task
    private Long taskId;
    private String title;
    private String content;
    private String registrantAccount;
    private LocalDateTime createdDate;
    private LocalDateTime expireDate;

    private Long projectId;

    // milestone
    private Long milestoneId;
    private String milestoneName;
    private LocalDateTime startDate;
    private LocalDateTime milestoneExpireDate;


    public TaskResponse entityToDto(Task task) {
        this.taskId = task.getTaskId();
        this.title = task.getTitle();
        this.content = task.getContent();
        this.registrantAccount = task.getRegistrantAccount();
        this.createdDate = task.getCreatedDate();
        this.expireDate = task.getExpireDate();

        this.projectId = task.getProject().getProjectId();

        if (Objects.nonNull(task.getMilestone())) {
            this.milestoneId = task.getMilestone().getMilestoneId();
            this.milestoneName = task.getMilestone().getMilestoneName();
            this.startDate = task.getMilestone().getStartDate();
            this.milestoneExpireDate = task.getMilestone().getMilestoneExpireDate();
        }

        return this;
    }

    public TaskResponse(Long taskId, String title, String content, String registrantAccount, LocalDateTime createdDate,
                        LocalDateTime expireDate, Long projectId, Long milestoneId, String milestoneName,
                        LocalDateTime startDate, LocalDateTime milestoneExpireDate) {
        this.taskId = taskId;
        this.title = title;
        this.content = content;
        this.registrantAccount = registrantAccount;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.projectId = projectId;
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
        this.startDate = startDate;
        this.milestoneExpireDate = milestoneExpireDate;
    }

    public TaskResponse() {
    }
}

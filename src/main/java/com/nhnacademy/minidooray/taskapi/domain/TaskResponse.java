package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Task;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class TaskResponse {
    private Long taskId;
    private String title;
    private String content;
    private String registrantAccount;
    private LocalDateTime createdDate;
    private LocalDateTime expireDate;

    private Long milestoneId;
    private Long projectId;

    public TaskResponse entityToDto(Task task) {
        this.taskId = task.getTaskId();
        this.content = task.getContent();
        this.title = task.getTitle();
        this.registrantAccount = task.getRegistrantAccount();
        this.createdDate = task.getCreatedDate();
        this.expireDate = task.getExpireDate();
        if (Objects.nonNull(task.getMilestone())) {
            this.milestoneId = task.getMilestone().getMilestoneId();
        }
        this.projectId = task.getProject().getProjectId();

        return this;
    }

    public TaskResponse(Long taskId, String title, String content, String registrantAccount, LocalDateTime createdDate,
                        LocalDateTime expireDate, Long milestoneId, Long projectId) {
        this.taskId = taskId;
        this.title = title;
        this.content = content;
        this.registrantAccount = registrantAccount;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.milestoneId = milestoneId;
        this.projectId = projectId;
    }

    public TaskResponse() {
    }
}

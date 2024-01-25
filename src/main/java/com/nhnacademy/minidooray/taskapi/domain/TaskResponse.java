package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Task;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long taskId;
    private String title;
    private String content;
    private String registrantAccount;
    private LocalDateTime createdDate;
    private LocalDateTime expireDate;

    private Long projectId;
    private Long milestoneId;

    public TaskResponse entityToDto(Task task) {
        this.taskId = task.getTaskId();
        this.content = task.getContent();
        this.title = task.getTitle();
        this.registrantAccount = task.getRegistrantAccount();
        this.createdDate=task.getCreatedDate();
        this.expireDate = task.getExpireDate();
        if (Objects.nonNull(task.getMilestone())) {
            this.milestoneId = task.getMilestone().getMilestoneId();
        }
        this.projectId = task.getProject().getProjectId();

        return this;
    }
}

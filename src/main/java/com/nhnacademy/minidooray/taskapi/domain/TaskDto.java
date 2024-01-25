package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Task;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//public interface TaskDto {
//    Long getTaskId();
//    String getTitle();
//    String getContent();
//    String getRegistrantAccount();
//    LocalDateTime getCreatedDate();
//    LocalDateTime getExpireDate();
//
//    Milestone getMilestone();
//    Project getProject();
//
//    interface Milestone {
//        Long getMilestoneId();
//    }
//
//    interface Project {
//        Long getProjectId();
//    }
//
//
//}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    Long taskId;
    String title;
    String content;
    String registrantAccount;
    LocalDateTime createdDate;
    LocalDateTime expireDate;

    Long projectId;
    Long milestoneId;

    public TaskDto entityToDto(Task task) {
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

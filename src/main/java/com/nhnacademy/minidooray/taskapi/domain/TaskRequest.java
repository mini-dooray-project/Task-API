package com.nhnacademy.minidooray.taskapi.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {
    Long milestoneId;
    Long projectId;
    @NotBlank
    String title;
    String content;
    @NotBlank
    String registrantAccount;
    LocalDateTime expireDate;

    public TaskRequest(Long milestoneId, Long projectId, String title, String content, String registrantAccount, LocalDateTime expireDate) {
        this.milestoneId = milestoneId;
        this.projectId = projectId;
        this.title = title;
        this.content = content;
        this.registrantAccount = registrantAccount;
        this.expireDate = expireDate;
    }
}

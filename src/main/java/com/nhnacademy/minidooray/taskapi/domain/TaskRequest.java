package com.nhnacademy.minidooray.taskapi.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TaskRequest {
    Long milestoneId;
    Long projectId;
    @NotBlank
    String title;
    String content;
    @NotBlank
    String registrantAccount;
    LocalDateTime expireDate;
}

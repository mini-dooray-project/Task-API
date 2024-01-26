package com.nhnacademy.minidooray.taskapi.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TaskRequest {
    Long milestoneId;
    Long projectId;
    @NotBlank
    String title;
    String content;
    String registrantAccount;
    LocalDateTime expireDate;
}

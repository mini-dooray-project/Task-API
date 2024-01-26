package com.nhnacademy.minidooray.taskapi.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MilestoneRequest {
    private Long projectId;
    @NotBlank
    private String milestoneName;
    private LocalDateTime startDate;
    private LocalDateTime milestoneExpireDate;
}

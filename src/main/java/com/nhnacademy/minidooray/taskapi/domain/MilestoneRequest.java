package com.nhnacademy.minidooray.taskapi.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MilestoneRequest {
    private Long projectId;
    private String milestoneName;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;
}

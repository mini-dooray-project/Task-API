package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class TaskResponseByProjectId {
    private Long taskId;
    private String taskTitle;
    private String registrantAccount;
    private String milestoneName;

    public TaskResponseByProjectId(Long taskId, String taskTitle, String registrantAccount, String milestoneName) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.registrantAccount = registrantAccount;
        this.milestoneName = milestoneName;
    }

    public TaskResponseByProjectId() {
    }
}

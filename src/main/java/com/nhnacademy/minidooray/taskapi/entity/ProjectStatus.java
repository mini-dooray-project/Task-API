package com.nhnacademy.minidooray.taskapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "project_status")
public class ProjectStatus {
    @Id
    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "status_name")
    private String statusName;
}

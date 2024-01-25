package com.nhnacademy.minidooray.taskapi.controller.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @JoinColumn(name = "status_id")
    @OneToOne
    private ProjectStatus status;

    @Column(name = "project_name")
    private String projectName;
}

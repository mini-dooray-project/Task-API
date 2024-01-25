package com.nhnacademy.minidooray.taskapi.controller.entity;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Task {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String title;

    private String content;

    @Column(name = "registrant_account")
    private String registrantAccount;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @JoinColumn(name = "milestone_id")
    @OneToOne
    private Milestone milestone;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;
}

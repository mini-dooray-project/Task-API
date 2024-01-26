package com.nhnacademy.minidooray.taskapi.entity;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "task")
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

    public Task() {
    }

    public Task(String title, String content, String registrantAccount, LocalDateTime createdDate,
                LocalDateTime expireDate, Milestone milestone, Project project) {
        this.title = title;
        this.content = content;
        this.registrantAccount = registrantAccount;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
        this.milestone = milestone;
        this.project = project;
    }

    public Task updateTask(String title, String content, String registrantAccount,
                           LocalDateTime expireDate, Milestone milestone, Project project) {
        this.title = title;
        this.content = content;
        this.registrantAccount = registrantAccount;
        this.expireDate = expireDate;
        this.milestone = milestone;
        this.project = project;

        return this;
    }
}

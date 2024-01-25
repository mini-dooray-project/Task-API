package com.nhnacademy.minidooray.taskapi.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @JoinColumn(name = "task_id")
    @ManyToOne
    private Task task;

    @Column(name = "registrant_account")
    private String registrantAccount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private String content;

    public Comment updateComment(Task task, String registrantAccount, String content) {
        this.task = task;
        this.registrantAccount = registrantAccount;
        this.content = content;

        return this;
    }

    public Comment(Task task, String registrantAccount, LocalDateTime createdDate, String content) {
        this.task = task;
        this.registrantAccount = registrantAccount;
        this.createdDate = createdDate;
        this.content = content;
    }

    public Comment() {
    }
}

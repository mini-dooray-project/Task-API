package com.nhnacademy.minidooray.taskapi.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    private Long commentId;

    @JoinColumn(name = "task_id")
    @ManyToOne
    private Task task;

    @Column(name = "registrant_account")
    private String registrantAccount;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    private String content;

}

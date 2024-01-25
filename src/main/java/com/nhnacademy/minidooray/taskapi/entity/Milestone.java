package com.nhnacademy.minidooray.taskapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "milestone")
public class Milestone {
    @Id
    @Column(name = "milestone_id")
    private Long milestoneId;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @Column(name = "milestone_name")
    private String milestoneName;

}

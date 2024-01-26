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
@Table(name = "milestone")
public class Milestone {
    @Id
    @Column(name = "milestone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @Column(name = "milestone_name")
    private String milestoneName;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    public Milestone updateMilestone(Project project, String milestoneName, LocalDateTime startDate,
                                     LocalDateTime expireDate) {
        this.project = project;
        this.milestoneName = milestoneName;
        this.startDate = startDate;
        this.expireDate = expireDate;

        return this;
    }

    public Milestone(Project project, String milestoneName, LocalDateTime startDate, LocalDateTime expireDate) {
        this.project = project;
        this.milestoneName = milestoneName;
        this.startDate = startDate;
        this.expireDate = expireDate;
    }

    public Milestone() {
    }
}

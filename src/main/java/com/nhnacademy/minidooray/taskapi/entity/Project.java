package com.nhnacademy.minidooray.taskapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @JoinColumn(name = "status_id")
    @OneToOne
    private ProjectStatus projectStatus;

    @Column(name = "project_name")
    private String projectName;

    public Project(ProjectStatus projectStatus, String projectName) {
        this.projectStatus = projectStatus;
        this.projectName = projectName;
    }

    public Project() {
    }

    public Project updateProject(String projectName, ProjectStatus projectStatus) {
        this.projectName = projectName;
        this.projectStatus = projectStatus;

        return this;
    }
}

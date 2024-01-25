package com.nhnacademy.minidooray.taskapi.controller.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tag {
    @Id
    @Column(name = "tag_id")
    private Long tagId;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @Column(name = "tag_name")
    private String tagName;
}

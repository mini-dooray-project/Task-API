package com.nhnacademy.minidooray.taskapi.entity;


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
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @Column(name = "tag_name")
    private String tagName;

    public Tag(Project project, String tagName) {
        this.project = project;
        this.tagName = tagName;
    }

    public Tag() {
    }

    public Tag updateTag(Project project, String tagName) {
        this.project = project;
        this.tagName = tagName;

        return this;
    }
}

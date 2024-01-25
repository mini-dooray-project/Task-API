package com.nhnacademy.minidooray.taskapi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "task_tag")
public class TaskTag {
    @EmbeddedId
    private Pk pk;

    @MapsId("taskId")
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @MapsId("tagId")
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @EqualsAndHashCode
    @Setter
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "tag_id")
        private Long tagId;
        @Column(name = "task_id")
        private Long taskId;
    }
}

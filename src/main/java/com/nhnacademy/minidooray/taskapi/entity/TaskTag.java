package com.nhnacademy.minidooray.taskapi.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @NoArgsConstructor
    @AllArgsConstructor
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

    public TaskTag updateTaskTag(Long tagId) {
        this.pk.tagId = tagId;
        return this;
    }

    public TaskTag(Pk pk, Task task, Tag tag) {
        this.pk = pk;
        this.task = task;
        this.tag = tag;
    }

    public TaskTag() {
    }
}

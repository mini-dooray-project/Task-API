package com.nhnacademy.minidooray.taskapi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "project_member")
public class ProjectMember {
    @EmbeddedId
    private Pk pk;

    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @JoinColumn(name = "authority_id")
    @OneToOne
    private ProjectAuthority projectAuthority;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "account_id")
        private String memberId;
        @Column(name = "project_id")
        private Long projectId;
    }

    public ProjectMember(Pk pk, Project project, ProjectAuthority projectAuthority) {
        this.pk = pk;
        this.project = project;
        this.projectAuthority = projectAuthority;
    }

    public ProjectMember() {
    }

    public ProjectMember updateProjectMember(ProjectAuthority authority) {
        this.projectAuthority = authority;

        return this;
    }
}

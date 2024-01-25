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
import lombok.EqualsAndHashCode;
import lombok.Getter;
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

    @EqualsAndHashCode
    @Setter
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "account_id")
        private String accountId;
        @Column(name = "project_id")
        private Long projectId;
    }
}

package com.matching.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table
@NoArgsConstructor
public class UserProject implements Serializable {

    @EmbeddedId
    private UserProjectKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("user_idx")
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("project_idx")
    @JoinColumn(name = "project_idx")
    private Project project;

    @Column
    private String status;

    @Column
    private String position;

    @Column
    private String simpleProfile;

    @Builder
    public UserProject(UserProjectKey id, User user, Project project, String status, String position, String simpleProfile) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.status = status;
        this.position = position;
        this.simpleProfile = simpleProfile;
    }
}


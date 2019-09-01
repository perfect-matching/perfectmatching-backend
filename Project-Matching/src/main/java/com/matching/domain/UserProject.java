package com.matching.domain;

import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.domain.key.UserProjectKey;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private UserProjectStatus status;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PositionType position;

    @Column(length = 400)
    private String simpleProfile;

    @Builder
    public UserProject(UserProjectKey id, User user, Project project, UserProjectStatus status, PositionType position, String simpleProfile) {
        this.id = id;
        this.user = user;
        this.project = project;
        this.status = status;
        this.position = position;
        this.simpleProfile = simpleProfile;
    }
}


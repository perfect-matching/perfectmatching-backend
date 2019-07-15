package com.matching.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author dongh9508
 * @since  2019-07-15
 */
@Entity
@Data
@Table
public class UserProject implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("user_idx")
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("project_idx")
    @JoinColumn(name = "project_idx")
    private Project project;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    @Length(max = 10)
    private String position;

    public UserProject(User user, Project project, boolean status, @Length(max = 10) String position) {
        this.user = user;
        this.project = project;
        this.status = status;
        this.position = position;
    }
}

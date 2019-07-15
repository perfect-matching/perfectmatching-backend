package com.matching.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author dongh9508
 * @since  2019-07-16
 */
@Entity
@Data
@Table
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    @Length(max = 30)
    private String email;

    @Column(nullable = false)
    @Length(max = 30)
    private String password;

    @Column(nullable = false)
    @Length(max = 30)
    private String nick;

    @Column
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserProject> userProjects;

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
    private List<Project> projects;

    public User(@Length(max = 30) String email, @Length(max = 30) String password, @Length(max = 30) String nick, LocalDateTime createdDate, List<Comment> comments, List<UserProject> userProjects, List<Project> projects) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.createdDate = createdDate;
        this.comments = comments;
        this.userProjects = userProjects;
        this.projects = projects;
    }
}

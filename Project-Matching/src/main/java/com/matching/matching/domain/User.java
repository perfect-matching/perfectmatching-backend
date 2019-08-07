package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"projects", "userProjects", "comments"})
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserProject> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Project> projects = new ArrayList<>();

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String nick;

    @Column
    private LocalDateTime createdDate;

    @Column
    private String profileImg;

    @Column
    private String description;

    @Builder
    public User(String email, String password, String nick, LocalDateTime createdDate, String profileImg, String description) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.createdDate = createdDate;
        this.profileImg = profileImg;
        this.description = description;
    }

    public void addComment(Comment comment) {
        comment.setWriter(this);
        this.comments.add(comment);
    }

    public void addUserProject(UserProject userProject) {
        userProject.setUser(this);
        this.userProjects.add(userProject);
    }

    public void addProject(Project project) {
        project.setLeader(this);
        this.projects.add(project);
    }

}

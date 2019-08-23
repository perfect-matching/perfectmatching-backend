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
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = "idx")
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
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<DoneProject> doneProjects = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserSkill> userSkills = new HashSet<>();

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 30)
    private String nick;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false, length = 100)
    private String profileImg;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Integer investTime;

    @Column(length = 100)
    private String socialUrl;

    @Builder
    public User(String email, String password, String nick, LocalDateTime createdDate, String profileImg, String description, Integer investTime, String socialUrl) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.createdDate = createdDate;
        this.profileImg = profileImg;
        this.description = description;
        this.investTime = investTime;
        this.socialUrl = socialUrl;
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

    public void addDoneProject(DoneProject doneProject) {
        doneProject.setUser(this);
        this.doneProjects.add(doneProject);
    }

    public void addUserSkill(UserSkill userSkill) {
        userSkill.setUser(this);
        this.userSkills.add(userSkill);
    }

}

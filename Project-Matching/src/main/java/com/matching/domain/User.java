package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserProject> userProjects = new HashSet<>();

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<>();

    @NotNull
    @Length(max = 30)
    @Column
    private String email;

    @NotNull
    @Length(max = 30)
    @Column
    @JsonIgnore
    private String password;

    @NotNull
    @Length(max = 30)
    @Column
    private String nick;

    @NotNull
    @Column
    private LocalDateTime createdDate;

    @Builder
    public User( @NotNull @Length(max = 30) String email, @NotNull @Length(max = 30) String password, @NotNull @Length(max = 30) String nick, @NotNull LocalDateTime createdDate) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.createdDate = createdDate;
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

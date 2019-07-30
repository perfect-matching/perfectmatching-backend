package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userProjects", "comments"})
public class Project implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User leader;

    @NotNull
    @Column
    private String title;

    @NotNull
    @Length(max = 5000)
    @Column
    private String content;

    @NotNull
    @Length(max = 10)
    @Column
    private String status;

    @NotNull
    @Column
    private String location;

    @Column
    @NotNull
    private LocalDateTime createdDate;

    @Column
    @NotNull
    private LocalDateTime deadline;

    @Column
    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @Column
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserProject> userProjects = new HashSet<>();

    @Builder
    public Project(@NotNull User leader, @NotNull String title, @NotNull @Length(max = 400000) String content,
                   @NotNull @Length(max = 10) String status, @NotNull String location, @NotNull LocalDateTime createdDate,
                   @NotNull LocalDateTime deadline, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate,
                   LocalDateTime modifiedDate) {
        this.leader = leader;
        this.title = title;
        this.content = content;
        this.status = status;
        this.location = location;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modifiedDate = modifiedDate;
    }

    public void addComment(Comment comment) {
        comment.setProject(this);
        this.comments.add(comment);
    }

    public void addUserProject(UserProject userProject) {
        userProject.setProject(this);
        this.userProjects.add(userProject);
    }

}

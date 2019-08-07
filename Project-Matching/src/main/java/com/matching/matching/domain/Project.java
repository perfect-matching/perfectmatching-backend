package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@EqualsAndHashCode(exclude = {"userProjects", "comments"})
public class Project implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    private User leader;

    @Column
    private String title;

    @Column(length = 4000)
    private String content;

    @Column
    private String status;

    @Column
    private String location;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime deadline;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column
    private Integer developerRecruits;

    @Column
    private Integer designerRecruits;

    @Column
    private Integer plannerRecruits;

    @Column
    private Integer marketerRecruits;

    @Column
    private Integer etcRecruits;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserProject> userProjects = new HashSet<>();

    @Builder
    public Project(User leader, String title, String content, String status, String location, LocalDateTime createdDate,
                   LocalDateTime deadline, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime modifiedDate,
                   Integer developerRecruits, Integer designerRecruits, Integer plannerRecruits, Integer marketerRecruits, Integer etcRecruits) {
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
        this.developerRecruits = developerRecruits;
        this.designerRecruits = designerRecruits;
        this.plannerRecruits = plannerRecruits;
        this.marketerRecruits = marketerRecruits;
        this.etcRecruits = etcRecruits;
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

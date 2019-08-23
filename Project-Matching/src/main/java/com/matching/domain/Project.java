package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
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
public class Project implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.EAGER)
    private User leader;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String content;

    @Column(nullable = false, length = 100)
    private String summary;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationType location;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime deadline;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private Integer developerRecruits;

    @Column(nullable = false)
    private Integer designerRecruits;

    @Column(nullable = false)
    private Integer plannerRecruits;

    @Column(nullable = false)
    private Integer marketerRecruits;

    @Column(nullable = false)
    private Integer etcRecruits;

    @Column(length = 100)
    private String socialUrl;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserProject> userProjects = new HashSet<>();

    @Builder
    public Project(User leader, String title, String content, String summary, ProjectStatus status, LocationType location, LocalDateTime createdDate, LocalDateTime deadline, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime modifiedDate, Integer developerRecruits, Integer designerRecruits, Integer plannerRecruits, Integer marketerRecruits, Integer etcRecruits, String socialUrl) {
        this.leader = leader;
        this.title = title;
        this.content = content;
        this.summary = summary;
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
        this.socialUrl = socialUrl;
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

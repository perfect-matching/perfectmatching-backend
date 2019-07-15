package com.matching.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dongh9508
 * @since  2019-07-16
 */
@Entity
@Data
@Table
public class Project implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    private User leader;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Length(max = 400000)
    private String content;

    @Column(nullable = false)
    @Length(max = 30)
    private String status;

    @Column(nullable = false)
    private String location;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime deadline;

    @Column
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<UserProject> userProjects;

    public Project(User leader, String title, @Length(max = 400000) String content, @Length(max = 30) String status, String location, LocalDateTime createdDate, LocalDateTime deadline, LocalDateTime modifiedDate, List<Comment> comments, List<UserProject> userProjects) {
        this.leader = leader;
        this.title = title;
        this.content = content;
        this.status = status;
        this.location = location;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
        this.userProjects = userProjects;
    }
}

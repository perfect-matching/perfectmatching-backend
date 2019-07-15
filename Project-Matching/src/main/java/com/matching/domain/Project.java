package com.matching.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table
public class Project implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

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

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Project(String title, String content, String status, String location, LocalDateTime createdDate, LocalDateTime deadline, LocalDateTime modifiedDate, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.location = location;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }
}

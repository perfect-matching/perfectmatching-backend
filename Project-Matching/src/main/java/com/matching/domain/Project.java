package com.matching.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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


}

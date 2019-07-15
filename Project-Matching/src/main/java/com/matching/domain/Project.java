package com.matching.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Data
@Table
public class Project {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
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
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<UserProject> user_projects = new ArrayList<>();

}

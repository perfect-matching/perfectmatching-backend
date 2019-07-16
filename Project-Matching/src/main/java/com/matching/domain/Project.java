package com.matching.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    @ManyToOne
    private User leader;

    @NotNull
    @Column
    private String title;

    @NotNull
    @Length(max = 400000)
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
    private LocalDateTime deadline;

    @Column
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<UserProject> userProjects;

}

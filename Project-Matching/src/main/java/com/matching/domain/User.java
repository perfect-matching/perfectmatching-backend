package com.matching.domain;

import lombok.*;
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
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserProject> userProjects;

    @OneToMany(mappedBy = "leader", fetch = FetchType.LAZY)
    private List<Project> projects;

    @NotNull
    @Length(max = 30)
    @Column
    private String email;

    @NotNull
    @Length(max = 30)
    @Column
    private String password;

    @NotNull
    @Length(max = 30)
    @Column
    private String nick;

    @NotNull
    @Column
    private LocalDateTime createdDate;

}

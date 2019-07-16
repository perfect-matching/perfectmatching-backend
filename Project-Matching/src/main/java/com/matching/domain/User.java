package com.matching.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * @author dongh9508
 * @since  2019-07-16
 */
@Entity
@Data
@Table
@NoArgsConstructor
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

}

package com.matching.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * @author dongh9508
 * @since  2019-07-15
 */
@Entity
@Data
@Table
public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    @Length(max = 30)
    private String email;

    @Column(nullable = false)
    @Length(max = 30)
    private String password;

    @Column(nullable = false)
    @Length(max = 30)
    private String nickname;

    @Column
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User(String email, String password, String nickname, LocalDateTime createdDate, List<Comment> comments) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.comments = comments;
    }
}

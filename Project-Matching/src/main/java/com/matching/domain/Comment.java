package com.matching.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author dongh9508
 * @since  2019-07-15
 */
@Entity
@Data
@Table
public class Comment implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotBlank
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotBlank
    private Project project;

    public Comment(String content, LocalDateTime createdDate, LocalDateTime modifiedDate, @NotBlank User user, @NotBlank Project project) {
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.user = user;
        this.project = project;
    }
}

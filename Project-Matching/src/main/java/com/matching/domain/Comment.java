package com.matching.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author dongh9508
 * @since  2019-07-15
 */
@Entity
@Setter
@Getter
@ToString
@Data
@Table
public class Comment {

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
}

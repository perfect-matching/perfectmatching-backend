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

    @ManyToOne(fetch = FetchType.EAGER)
    @NotBlank
    private User writer;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotBlank
    private Project project;


}

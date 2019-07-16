package com.matching.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author dongh9508
 * @since  2019-07-15
 */
@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User writer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    @NotNull
    @Column
    private String content;

    @NotNull
    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;



}

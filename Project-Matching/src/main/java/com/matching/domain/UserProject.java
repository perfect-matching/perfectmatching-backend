package com.matching.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dongh9508
 * @since  2019-07-15
 */
@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class UserProject implements Serializable {
    @EmbeddedId
    private UserProjectKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("user_idx")
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("project_idx")
    @JoinColumn(name = "project_idx")
    private Project project;

    @NotNull
    @Length(max = 10)
    @Column
    private String status;

    @NotNull
    @Length(max = 10)
    @Column
    private String position;

}

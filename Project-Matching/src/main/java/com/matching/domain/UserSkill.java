package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = "idx")
@Relation(collectionRelation = "datas")
public class UserSkill implements Serializable  {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Builder
    public UserSkill(String text, User user) {
        this.text = text;
        this.user = user;
    }
}

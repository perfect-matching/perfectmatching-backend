package com.matching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = {"idx", "text"})
@Relation(collectionRelation = "datas")
public class Tag implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProjectTag> projectTags = new HashSet<>();

    @Builder
    public Tag(String text) {
        this.text = text;
    }

    public void addProjectTag(ProjectTag projectTag) {
        projectTag.setTag(this);
        this.projectTags.add(projectTag);
    }
}

package com.matching.domain;

import com.matching.domain.key.ProjectTagKey;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProjectTag implements Serializable {

    @EmbeddedId
    private ProjectTagKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("project_idx")
    @JoinColumn(name = "project_idx")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tag_idx")
    @JoinColumn(name = "tag_idx")
    private Tag tag;

    @Builder
    public ProjectTag(ProjectTagKey id, Project project, Tag tag) {
        this.id = id;
        this.project = project;
        this.tag = tag;
    }
}

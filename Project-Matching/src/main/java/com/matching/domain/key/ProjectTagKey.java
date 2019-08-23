package com.matching.domain.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTagKey implements Serializable {

    @Column(name = "project_idx")
    private Long projectIdx;

    @Column(name = "tag_idx")
    private Long tagIdx;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        ProjectTagKey projectTagKey = (ProjectTagKey) o;
        return Objects.equals( projectIdx, projectTagKey.projectIdx ) && Objects.equals( tagIdx, projectTagKey.tagIdx );
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectIdx, tagIdx);
    }
}

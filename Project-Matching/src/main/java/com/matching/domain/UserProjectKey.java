package com.matching.domain;

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
public class UserProjectKey implements Serializable {

    @Column(name = "user_idx")
    Long userIdx;

    @Column(name = "project_idx")
    Long projectIdx;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        UserProjectKey userProjectKey = (UserProjectKey)o;
        return Objects.equals( userIdx, userProjectKey.userIdx ) && Objects.equals( projectIdx, userProjectKey.projectIdx );
    }

    @Override
    public int hashCode() {
        return Objects.hash( userIdx, projectIdx );
    }
}

package com.matching.domain.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectKey implements Serializable {

    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "project_idx")
    private Long projectIdx;
}

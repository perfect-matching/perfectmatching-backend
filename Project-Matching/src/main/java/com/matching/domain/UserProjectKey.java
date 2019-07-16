package com.matching.domain;

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
    Long userIdx;

    @Column(name = "project_idx")
    Long projectIdx;

}

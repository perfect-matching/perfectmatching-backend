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
public class ProjectTagKey implements Serializable {

    @Column(name = "project_idx")
    private Long projectIdx;

    @Column(name = "tag_idx")
    private Long tagIdx;

}

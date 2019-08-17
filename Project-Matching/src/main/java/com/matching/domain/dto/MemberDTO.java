package com.matching.domain.dto;

import com.matching.domain.UserProject;
import lombok.Data;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;

@Data
@Relation(collectionRelation = "datas")
public class MemberDTO {

    @NotBlank
    private Long memberIdx;

    @NotBlank
    private Long projectIdx;

    @NotBlank
    private String memberNick;

    @NotBlank
    private String position;

    public MemberDTO(UserProject userProject) {
        this.memberIdx = userProject.getUser().getIdx();
        this.projectIdx = userProject.getProject().getIdx();
        this.memberNick = userProject.getUser().getNick();
        this.position = userProject.getPosition().getPosition();
    }
}

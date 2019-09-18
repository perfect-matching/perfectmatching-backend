package com.matching.domain.dto;

import com.matching.domain.UserProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@Relation(collectionRelation = "datas")
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long memberIdx;

    private Long projectIdx;

    private String profileImage;

    @NotBlank
    private String memberNick;

    @NotBlank
    private String position;

    private String simpleProfile;

    public MemberDTO(UserProject userProject) {
        this.memberIdx = userProject.getUser().getIdx();
        this.profileImage = userProject.getUser().getProfileImg();
        this.projectIdx = userProject.getProject().getIdx();
        this.memberNick = userProject.getUser().getNick();
        this.simpleProfile = userProject.getSimpleProfile();
        this.position = userProject.getPosition().getPosition();
    }
}

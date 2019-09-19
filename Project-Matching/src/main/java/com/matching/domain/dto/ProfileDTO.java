package com.matching.domain.dto;

import com.matching.domain.User;
import com.matching.domain.UserSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Long userIdx;

    @NotBlank
    @Length(max = 30, min = 1)
    private String nickname;

    @NotBlank
    @Length(max = 500)
    private String summary;

    @NotBlank
    @Length(max = 100)
    private String profileImage;

    @NotBlank
    @Email
    @Length(max = 30, min = 3)
    private String email;

    @NotNull
    private Integer investTime;

    @Length(max = 100)
    private String socialUrl;

    private Set<UserSkill> userSkills = new HashSet<>();

    public ProfileDTO(User user) {
        this.userIdx = user.getIdx();
        this.nickname = user.getNick();
        this.summary = user.getDescription();
        this.profileImage = user.getProfileImg();
        this.email = user.getEmail();
        this.investTime = user.getInvestTime();
        this.socialUrl = user.getSocialUrl();
    }

}

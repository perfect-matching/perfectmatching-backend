package com.matching.domain.dto;

import com.matching.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    @NotBlank
    private Long userIdx;

    @NotBlank
    @Length(max = 30, min = 1)
    private String nickname;

    @NotBlank
    @Length(max = 500)
    private String summary;

    @NotBlank
    @Email
    @Length(max = 30, min = 3)
    private String email;

    public ProfileDTO(User user) {
        this.userIdx = user.getIdx();
        this.nickname = user.getNick();
        this.summary = user.getDescription();
        this.email = user.getEmail();
    }

}

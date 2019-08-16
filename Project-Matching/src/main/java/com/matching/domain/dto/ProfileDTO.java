package com.matching.domain.dto;

import com.matching.domain.User;
import com.matching.domain.UserProject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
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

    private List<ProfileProjectDTO> datas = new ArrayList<>();

    public ProfileDTO(User user) {
        this.userIdx = user.getIdx();
        this.nickname = user.getNick();
        this.summary = user.getDescription();
        this.email = user.getEmail();

        for(UserProject userProject : user.getUserProjects()) {
            datas.add(new ProfileProjectDTO(userProject));
        }
    }
}

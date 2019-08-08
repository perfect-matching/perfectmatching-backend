package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileDTO {

    @NotBlank
    private String nickname;

    @NotBlank
    private String summary;

    @NotBlank
    private List<ProfileProjectDTO> profileProjectDTOS = new ArrayList<>();

    public ProfileDTO(User user) {
        this.nickname = user.getNick();
        this.summary = user.getDescription();

        for(UserProject userProject : user.getUserProjects()) {
            profileProjectDTOS.add(new ProfileProjectDTO(userProject));
        }
    }
}

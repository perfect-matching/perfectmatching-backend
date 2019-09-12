package com.matching.domain.dto;

import com.matching.domain.UserSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userIdx;

    @NotBlank(message = "이메일이 비어있습니다.")
    @Email(message = "유효한 이메일이 아닙니다. 이메일 형식인지 확인해주시길 바랍니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 22, message = "비밀번호는 8~22 사이로 작성해주서야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String password;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 22, message = "비밀번호는 8~22 사이로 작성해주서야 합니다. ")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,22}$", message = "비밀번호 구성을 올바르게 하십시오.")
    private String confirmPassword;

    @NotBlank(message = "닉네임이 비어있습니다.")
    @Length(max = 30, min = 2, message = "닉네임은 2~30 사이로 설정하시길 바랍니다.")
    private String nickname;

    @Length(max = 100, message = "프로필 이미지 URL은 100자 내외로 작성해주시길 바랍니다.")
    private String profileImag;

    @NotBlank(message = "자기소개가 비어있습니다.")
    @Length(max = 500, message = "자기소개는 500자 내외로 작성해주시길 바랍니다.")
    private String summary;

    @NotNull(message = "투자 시간이 비어있습니다.")
    private Integer investTime;

    @Length(max = 100, message = "URL은 100자 내외로 입력해주시길 바랍니다.")
    private String socialUrl;

    private Set<UserSkill> userSkills = new HashSet<>();

}

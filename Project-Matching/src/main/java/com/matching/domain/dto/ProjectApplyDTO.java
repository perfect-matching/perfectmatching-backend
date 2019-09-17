package com.matching.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplyDTO {

    @NotNull(message = "Project Idx가 비어있습니다.")
    private Long projectIdx;

    @NotBlank(message = "신청직군이 비어있습니다.")
    @Length(max = 10)
    private String position;

    @NotBlank(message = "지원동기가 비어있습니다.")
    @Length(max = 400)
    private String simpleProfile;
}

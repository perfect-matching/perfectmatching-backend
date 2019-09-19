package com.matching.domain.dto;

import com.matching.domain.DoneProject;
import com.matching.domain.UsedSkill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Relation(collectionRelation = "datas")
@NoArgsConstructor
@AllArgsConstructor
public class DoneProjectDTO {

    private Long doneProjectIdx;

    private Long projectIdx;

    private Long userIdx;

    private String profileImage;

    @NotBlank
    @Length(max = 255, min = 1)
    private String title;

    @NotBlank
    @Length(max = 100, min = 1)
    private String summary;

    @NotBlank
    @Length(max = 5000, min = 1)
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private String socialUrl;

    private Set<UsedSkill> tags = new HashSet<>();

    public DoneProjectDTO(DoneProject doneProject) {
        this.doneProjectIdx = doneProject.getIdx();
        this.projectIdx = doneProject.getProjectIdx();
        this.userIdx = doneProject.getUser().getIdx();
        this.profileImage = doneProject.getUser().getProfileImg();
        this.title = doneProject.getTitle();
        this.summary = doneProject.getSummary();
        this.content = doneProject.getContent();
        this.createdDate = doneProject.getCreatedDate();
        this.modifiedDate = doneProject.getModifiedDate();
        this.startDate = doneProject.getStartDate();
        this.endDate = doneProject.getEndDate();
        this.socialUrl = doneProject.getSocialUrl();
        this.tags.addAll(doneProject.getUsedSkills());
    }
}

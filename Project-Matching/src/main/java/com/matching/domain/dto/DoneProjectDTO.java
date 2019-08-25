package com.matching.domain.dto;

import com.matching.domain.DoneProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@Relation(collectionRelation = "datas")
@NoArgsConstructor
@AllArgsConstructor
public class DoneProjectDTO {

    private Long doneProjectIdx;

    private Long projectIdx;

    private Long userIdx;

    @NotBlank
    @Length(max = 255, min = 1)
    private String title;

    @NotBlank
    @Length(max = 100, min = 1)
    private String summary;

    @NotBlank
    @Length(max = 5000, min = 1)
    private String content;

    @NotBlank
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
    private LocalDateTime endDate;

    private String socialUrl;

    public DoneProjectDTO(DoneProject doneProject) {
        this.doneProjectIdx = doneProject.getIdx();
        this.projectIdx = doneProject.getProjectIdx();
        this.userIdx = doneProject.getUser().getIdx();
        this.title = doneProject.getTitle();
        this.summary = doneProject.getSummary();
        this.content = doneProject.getContent();
        this.createdDate = doneProject.getCreatedDate();
        this.modifiedDate = doneProject.getModifiedDate();
        this.startDate = doneProject.getStartDate();
        this.endDate = doneProject.getEndDate();
        this.socialUrl = doneProject.getSocialUrl();
    }
}

package com.matching.domain.dto;

import com.matching.domain.UserProject;
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
public class ProcessingProjectDTO {

    private Long userIdx;

    private Long projectIdx;

    @NotBlank
    private String leaderNick;

    @NotBlank
    @Length(max = 255, min = 1)
    private String title;

    @NotBlank
    @Length(max = 10, min = 1)
    private String status;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    @Length(max = 10, min = 1)
    private String position;

    @Length(max = 400)
    private String summary;

    public ProcessingProjectDTO(UserProject userProject) {
        this.userIdx = userProject.getUser().getIdx();
        this.projectIdx = userProject.getProject().getIdx();
        this.leaderNick = userProject.getUser().getNick();
        this.title = userProject.getProject().getTitle();
        this.status = userProject.getProject().getStatus().getStatus();
        this.createdDate = userProject.getProject().getCreatedDate();
        this.position = userProject.getPosition().getPosition();
        this.summary = userProject.getProject().getSummary();
    }
}

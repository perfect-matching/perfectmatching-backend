package com.matching.domain.dto;

import com.matching.domain.Project;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Relation(collectionRelation = "datas")
public class ProjectsDTO {

    @NotBlank
    private Long projectIdx;

    @NotBlank
    @Length(max = 30, min = 1)
    private String leader;

    @NotBlank
    private Long leaderIdx;

    @NotBlank
    @Length(max = 100, min = 1)
    private String leaderImage;

    @NotBlank
    @Length(max = 255, min = 1)
    private String title;

    @NotBlank
    @Length(max = 100, min = 1)
    private String summary;

    @NotBlank
    @Length(max = 255, min = 1)
    private String location;

    @NotBlank
    private Boolean developerRecruits;

    @NotBlank
    private Boolean designerRecruits;

    @NotBlank
    private Boolean plannerRecruits;

    @NotBlank
    private Boolean marketerRecruits;

    @NotBlank
    private Boolean etcRecruits;

    private LocalDateTime deadLine;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    @Length(max = 10, min = 1)
    private String status;

    public ProjectsDTO(Project project) {
        this.projectIdx = project.getIdx();
        this.leader = project.getLeader().getNick();
        this.leaderIdx = project.getLeader().getIdx();
        this.leaderImage = project.getLeader().getProfileImg();
        this.title = project.getTitle();
        this.summary = project.getSummary();
        this.deadLine = project.getDeadline();
        this.createdDate = project.getCreatedDate();
        this.status = project.getStatus().getStatus();
        this.location = project.getLocation().getLocation();
        this.developerRecruits = project.getDeveloperRecruits() > 0;
        this.designerRecruits = project.getDesignerRecruits() > 0;
        this.plannerRecruits = project.getPlannerRecruits() > 0;
        this.marketerRecruits = project.getMarketerRecruits() > 0;
        this.etcRecruits = project.getEtcRecruits() > 0;
    }

}

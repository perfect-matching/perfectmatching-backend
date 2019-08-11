package com.matching.domain.dto;

import com.matching.domain.Project;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ProjectsDTO {

    @NotBlank
    private String leader;

    @NotBlank
    private String leaderImage;

    @NotBlank
    private String title;

    @NotBlank
    private String summary;

    @NotBlank
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

    @NotBlank
    private LocalDateTime deadLine;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    @Length(max = 10)
    private String status;

    public ProjectsDTO(Project project) {
        this.leader = project.getLeader().getNick();
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

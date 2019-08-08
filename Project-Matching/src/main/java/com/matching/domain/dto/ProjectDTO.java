package com.matching.domain.dto;

import com.matching.domain.Project;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ProjectDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String leader;

    @NotBlank
    private String content;

    @NotBlank
    private String status;

    @NotBlank
    private String location;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime deadline;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
    private LocalDateTime endDate;

    @NotBlank
    private LocalDateTime modifiedDate;

    @NotBlank
    private Integer developerRecruits;

    @NotBlank
    private Integer designerRecruits;

    @NotBlank
    private Integer plannerRecruits;

    @NotBlank
    private Integer marketerRecruits;

    @NotBlank
    private Integer etcRecruits;

    @NotBlank
    private String summary;

    public ProjectDTO(Project project) {
        this.title = project.getTitle();
        this.leader = project.getLeader().getNick();
        this.content = project.getContent();
        this.status = project.getStatus();
        this.location = project.getLocation();
        this.createdDate = project.getCreatedDate();
        this.deadline = project.getDeadline();
        this.startDate = project.getStartDate();
        this.plannerRecruits = project.getPlannerRecruits();
        this.designerRecruits = project.getDesignerRecruits();
        this.plannerRecruits = project.getMarketerRecruits();
        this.endDate = project.getEndDate();
        this.etcRecruits = project.getEtcRecruits();
        this.summary = project.getSummary();
    }
}

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
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String location;

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
    private LocalDateTime deadLine;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    @Length(max = 10)
    private String status;

    public ProjectsDTO(Project project) {
        this.leader = project.getLeader().getNick();
        this.title = project.getTitle();
        this.content = project.getContent();
        this.deadLine = project.getDeadline();
        this.createdDate = project.getCreatedDate();
        this.status = project.getStatus();
        this.location = project.getLocation();
        this.developerRecruits = project.getDeveloperRecruits();
        this.designerRecruits = project.getDesignerRecruits();
        this.plannerRecruits = project.getPlannerRecruits();
        this.marketerRecruits = project.getMarketerRecruits();
        this.etcRecruits = project.getEtcRecruits();
    }

}
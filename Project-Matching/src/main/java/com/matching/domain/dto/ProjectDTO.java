package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.repository.UserProjectRepository;
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
    private Integer currentDeveloper;

    @NotBlank
    private Integer currentDesigner;

    @NotBlank
    private Integer currentPlanner;

    @NotBlank
    private Integer currentMarketer;

    @NotBlank
    private Integer currentEtc;

    @NotBlank
    private String summary;

    public ProjectDTO(Project project, UserProjectRepository userProjectRepo) {
        this.title = project.getTitle();
        this.leader = project.getLeader().getNick();
        this.content = project.getContent();
        this.status = project.getStatus().getStatus();
        this.location = project.getLocation().getLocation();
        this.createdDate = project.getCreatedDate();
        this.deadline = project.getDeadline();
        this.startDate = project.getStartDate();
        this.developerRecruits = project.getDeveloperRecruits();
        this.marketerRecruits = project.getMarketerRecruits();
        this.plannerRecruits = project.getPlannerRecruits();
        this.designerRecruits = project.getDesignerRecruits();
        this.plannerRecruits = project.getMarketerRecruits();
        this.endDate = project.getEndDate();
        this.etcRecruits = project.getEtcRecruits();
        this.summary = project.getSummary();
        this.currentDeveloper = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.DEVELOPER, UserProjectStatus.MATCHING);
        this.currentDesigner = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.DESIGNER, UserProjectStatus.MATCHING);
        this.currentMarketer = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.MARKETER, UserProjectStatus.MATCHING);
        this.currentPlanner = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.PLANNER, UserProjectStatus.MATCHING);
        this.currentEtc = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.ETC, UserProjectStatus.MATCHING);

    }
}

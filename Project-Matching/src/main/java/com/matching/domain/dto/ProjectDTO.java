package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.repository.UserProjectRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @NotBlank
    private Long projectIdx;

    @NotBlank
    @Length(max = 255, min = 1)
    private String title;

    @NotBlank
    private Long leaderIdx;

    @NotBlank
    @Length(max = 30, min = 1)
    private String leader;

    @NotBlank
    @Length(max = 5000, min = 1)
    private String content;

    @NotBlank
    @Length(max = 10, min = 1)
    private String status;

    @NotBlank
    @Length(max = 255, min = 1)
    private String location;

    @NotBlank
    private LocalDateTime createdDate;

    private LocalDateTime deadline;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
    private LocalDateTime endDate;

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
    @Length(max = 100, min = 1)
    private String summary;

    @Length(max = 100, min = 1)
    private String socialUrl;

    public ProjectDTO(Project project, UserProjectRepository userProjectRepo) {
        this.projectIdx = project.getIdx();
        this.title = project.getTitle();
        this.leader = project.getLeader().getNick();
        this.leaderIdx = project.getLeader().getIdx();
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
        this.socialUrl = project.getSocialUrl();

    }
}

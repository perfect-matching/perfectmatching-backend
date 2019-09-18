package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.ProjectTag;
import com.matching.domain.Tag;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.repository.UserProjectRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long projectIdx;

    @NotBlank
    @Length(max = 255, min = 1)
    private String title;

    private Long leaderIdx;

    @Length(max = 30, min = 1)
    private String leader;

    @Length(max = 5000)
    private String content;

    @Length(max = 10, min = 1)
    private String status;

    @NotBlank
    @Length(max = 255, min = 1)
    private String location;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @NotNull
    private Integer developerRecruits;

    @NotNull
    private Integer designerRecruits;

    @NotNull
    private Integer plannerRecruits;

    @NotNull
    private Integer marketerRecruits;

    @NotNull
    private Integer etcRecruits;

    private Integer currentDeveloper;

    private Integer currentDesigner;

    private Integer currentPlanner;

    private Integer currentMarketer;

    private Integer currentEtc;

    @NotBlank
    @Length(max = 100, min = 1)
    private String summary;

    @Length(max = 100)
    private String socialUrl;

    private Set<Tag> tags = new HashSet<>();

    private Set<Tag> addTags = new HashSet<>();

    private Set<Tag> removeTags = new HashSet<>();

    public ProjectDTO(Project project, UserProjectRepository userProjectRepo) {
        this.projectIdx = project.getIdx();
        this.title = project.getTitle();
        this.leader = project.getLeader().getNick();
        this.leaderIdx = project.getLeader().getIdx();
        this.content = project.getContent();
        this.status = project.getStatus().getStatus();
        this.location = project.getLocation().getLocation();
        this.createdDate = project.getCreatedDate();
        this.developerRecruits = project.getDeveloperRecruits();
        this.marketerRecruits = project.getMarketerRecruits();
        this.plannerRecruits = project.getPlannerRecruits();
        this.designerRecruits = project.getDesignerRecruits();
        this.plannerRecruits = project.getMarketerRecruits();
        this.etcRecruits = project.getEtcRecruits();
        this.summary = project.getSummary();
        this.currentDeveloper = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.DEVELOPER, UserProjectStatus.MATCHING);
        this.currentDesigner = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.DESIGNER, UserProjectStatus.MATCHING);
        this.currentMarketer = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.MARKETER, UserProjectStatus.MATCHING);
        this.currentPlanner = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.PLANNER, UserProjectStatus.MATCHING);
        this.currentEtc = userProjectRepo.countByProjectAndPositionAndStatus(project, PositionType.ETC, UserProjectStatus.MATCHING);
        this.socialUrl = project.getSocialUrl();

        for(ProjectTag projectTag : project.getProjectTags())
            tags.add(projectTag.getTag());
    }
}

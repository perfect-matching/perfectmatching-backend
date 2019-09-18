package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.ProjectTag;
import com.matching.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Relation(collectionRelation = "datas")
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsDTO {

    private Long projectIdx;

    @NotBlank
    @Length(max = 30, min = 1)
    private String leader;

    private Long leaderIdx;

    @Length(max = 100, min = 1)
    private String profileImage;

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

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    @Length(max = 10, min = 1)
    private String status;

    private Set<Tag> tags = new HashSet<>();

    public ProjectsDTO(Project project) {
        this.projectIdx = project.getIdx();
        this.leader = project.getLeader().getNick();
        this.leaderIdx = project.getLeader().getIdx();
        this.profileImage = project.getLeader().getProfileImg();
        this.title = project.getTitle();
        this.summary = project.getSummary();
        this.createdDate = project.getCreatedDate();
        this.status = project.getStatus().getStatus();
        this.location = project.getLocation().getLocation();
        this.developerRecruits = project.getDeveloperRecruits() > 0;
        this.designerRecruits = project.getDesignerRecruits() > 0;
        this.plannerRecruits = project.getPlannerRecruits() > 0;
        this.marketerRecruits = project.getMarketerRecruits() > 0;
        this.etcRecruits = project.getEtcRecruits() > 0;

        for(ProjectTag projectTag : project.getProjectTags())
            this.tags.add(projectTag.getTag());
    }

}

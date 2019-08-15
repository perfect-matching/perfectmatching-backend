package com.matching.domain.dto;

import com.matching.domain.UserProject;
import lombok.Data;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Relation(collectionRelation = "datas")
public class ProfileProjectDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String status;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private String position;

    @NotBlank
    private String summary;

    public ProfileProjectDTO(UserProject userProject) {
        this.title = userProject.getProject().getTitle();
        this.status = userProject.getProject().getStatus().getStatus();
        this.createdDate = userProject.getProject().getCreatedDate();
        this.position = userProject.getPosition().getPosition();
        this.summary = userProject.getProject().getSummary();
    }
}

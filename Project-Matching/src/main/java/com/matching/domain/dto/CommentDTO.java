package com.matching.domain.dto;

import com.matching.domain.Comment;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Relation(collectionRelation = "datas")
public class CommentDTO {

    @NotBlank
    private Long commentIdx;

    @NotBlank
    @Length(max = 30, min = 1)
    private String userName;

    @NotBlank
    private Long userIdx;

    @NotBlank
    @Length(max = 255, min = 1)
    private String projectTitle;

    @NotBlank
    private Long projectIdx;

    @NotBlank
    @Length(max = 255, min = 1)
    private String content;

    @NotBlank
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public CommentDTO(Comment comment) {
        this.commentIdx = comment.getIdx();
        this.userName = comment.getWriter().getNick();
        this.userIdx = comment.getWriter().getIdx();
        this.projectTitle = comment.getProject().getTitle();
        this.projectIdx = comment.getProject().getIdx();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}

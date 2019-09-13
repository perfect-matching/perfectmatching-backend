package com.matching.domain.dto;

import com.matching.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@Relation(collectionRelation = "datas")
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long commentIdx;

    @Length(max = 30, min = 1)
    private String userName;

    private Long userIdx;

    @Length(max = 255, min = 1)
    private String projectTitle;

    @NotNull(message = "project Idx가 비어있습니다.")
    private Long projectIdx;

    @NotBlank(message = "내용이 비어있습니다.")
    @Length(max = 255, min = 1)
    private String content;

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

package com.matching.domain.dto;

import com.matching.domain.Comment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CommentDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String projectTitle;

    @NotBlank
    private String content;

    @NotBlank
    private LocalDateTime createdDate;

    @NotBlank
    private LocalDateTime modifiedDate;

    public CommentDTO(Comment comment) {
        this.userName = comment.getWriter().getNick();
        this.projectTitle = comment.getProject().getTitle();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}

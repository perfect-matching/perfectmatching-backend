package com.matching.domain.dto;

import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentDTOTest {

    private Comment comment;

    @Before
    public void createUserAndProject() {
        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        Project project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();

        comment = Comment.builder().writer(user).project(project).createdDate(LocalDateTime.now()).content("테스트 댓글").build();
    }

    @Test
    public void createCommentDTOTest() {
        CommentDTO commentDTO = new CommentDTO(comment);

        assertThat(commentDTO).isNotNull();
        assertThat(commentDTO.getCommentIdx()).isEqualTo(comment.getIdx());
        assertThat(commentDTO.getContent()).isEqualTo(comment.getContent());
        assertThat(commentDTO.getCreatedDate()).isEqualTo(comment.getCreatedDate());
        assertThat(commentDTO.getProjectTitle()).isEqualTo(comment.getProject().getTitle());
        assertThat(commentDTO.getProjectIdx()).isEqualTo(comment.getProject().getIdx());
        assertThat(commentDTO.getUserIdx()).isEqualTo(comment.getWriter().getIdx());
        assertThat(commentDTO.getUserName()).isEqualTo(comment.getWriter().getNick());
    }

}
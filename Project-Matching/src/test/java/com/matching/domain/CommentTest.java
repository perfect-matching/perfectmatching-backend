package com.matching.domain;

import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class CommentTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    private User user1;

    private User user2;

    private Project project;

    @Before
    public void createUserAndProject() {
        user1 = User.builder().nick("Test User1").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user1);

        user2 = User.builder().nick("Test User2").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user2);

        project = Project.builder().leader(user1).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();
        testEntityManager.persist(project);
    }

    @Test
    public void createCommentTest() {
        Comment comment = Comment.builder().project(project).writer(user1).content("테스트 댓글").createdDate(LocalDateTime.now())
                                           .build();
        testEntityManager.persist(comment);
        assertThat(commentRepository.getOne(comment.getIdx())).isNotNull().isEqualTo(comment);
    }

    @Test
    public void createCommentAndSearchTest() {
        Comment comment1 = Comment.builder().project(project).writer(user1).content("테스트 댓글1").createdDate(LocalDateTime.now())
                .build();
        testEntityManager.persist(comment1);

        Comment comment2 = Comment.builder().project(project).writer(user2).content("테스트 댓글2").createdDate(LocalDateTime.now())
                .build();
        testEntityManager.persist(comment2);

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList.size()).isNotNull().isEqualTo(2);
        assertThat(commentList.get(0)).isNotNull().isEqualTo(comment1);
        assertThat(commentList.get(1)).isNotNull().isEqualTo(comment2);
    }

    @Test
    public void createCommentAndDeleteTest() {
        Comment comment1 = Comment.builder().project(project).writer(user1).content("테스트 댓글1").createdDate(LocalDateTime.now())
                .build();
        testEntityManager.persist(comment1);

        Comment comment2 = Comment.builder().project(project).writer(user2).content("테스트 댓글2").createdDate(LocalDateTime.now())
                .build();
        testEntityManager.persist(comment2);

        commentRepository.deleteAll();
        assertThat(commentRepository.findAll().isEmpty()).isTrue();
    }

}
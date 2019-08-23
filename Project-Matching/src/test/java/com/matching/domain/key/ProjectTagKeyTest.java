package com.matching.domain.key;

import com.matching.domain.Project;
import com.matching.domain.Tag;
import com.matching.domain.User;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ProjectTagKeyTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private Project project;

    private Tag tag;

    @Before
    public void createProjectAndTag() {
        User user = User.builder().nick("Test User1").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);

        project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();
        testEntityManager.persist(project);

        tag = Tag.builder().text("테스트 태그").build();
        testEntityManager.persist(tag);
    }

    @Test
    public void projectTagKeyCreateTest() {
        ProjectTagKey projectTagKey = new ProjectTagKey(project.getIdx(), tag.getIdx());

        assertThat(projectTagKey).isNotNull();
        assertThat(projectTagKey.getProjectIdx()).isEqualTo(project.getIdx());
        assertThat(projectTagKey.getTagIdx()).isEqualTo(tag.getIdx());
    }

}
package com.matching.domain.key;

import com.matching.domain.Project;
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
public class UserProjectKeyTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private Project project;

    private User user;

    @Before
    public void createUserAndProject() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user);

        project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project);
    }

    @Test
    public void userProjectKeyCreateTest() {
        UserProjectKey key = new UserProjectKey(user.getIdx(), project.getIdx());

        assertThat(key).isNotNull();
        assertThat(key.getUserIdx()).isEqualTo(user.getIdx());
        assertThat(key.getProjectIdx()).isEqualTo(project.getIdx());
    }
}
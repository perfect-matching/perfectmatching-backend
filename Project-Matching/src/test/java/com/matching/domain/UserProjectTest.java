package com.matching.domain;

import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.domain.key.UserProjectKey;
import com.matching.repository.UserProjectRepository;
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
public class UserProjectTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserProjectRepository userProjectRepository;

    private User user;

    private Project project;

    private Project project2;

    private UserProjectKey userProjectKey;

    private UserProjectKey userProjectKey2;

    @Before
    public void createUserAndProjectAndUserProjectKey() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);

        project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();
        testEntityManager.persist(project);

        project2 = Project.builder().leader(user).title("테스트 프로젝트2").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();
        testEntityManager.persist(project2);

        userProjectKey = new UserProjectKey(user.getIdx(), project.getIdx());
        userProjectKey2 = new UserProjectKey(user.getIdx(), project2.getIdx());
    }

    @Test
    public void createUserProjectTest() {
        UserProject userProject = UserProject.builder().id(userProjectKey).user(user).project(project).simpleProfile("자기소개")
                                             .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                                             .build();
        testEntityManager.persist(userProject);
        assertThat(userProjectRepository.getOne(userProject.getId())).isNotNull().isEqualTo(userProject);
    }

    @Test
    public void createUserProjectAndSearchTest() {
        UserProject userProject1 = UserProject.builder().id(userProjectKey).user(user).project(project).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();
        testEntityManager.persist(userProject1);

        UserProject userProject2 = UserProject.builder().id(userProjectKey2).user(user).project(project2).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();
        testEntityManager.persist(userProject2);

        List<UserProject> userProjectList = userProjectRepository.findAll();

        assertThat(userProjectList.size()).isNotNull().isEqualTo(2);
        assertThat(userProjectList.get(0)).isNotNull().isEqualTo(userProject1);
        assertThat(userProjectList.get(1)).isNotNull().isEqualTo(userProject2);
    }

    @Test
    public void createUserProjectAndDeleteTest() {
        UserProject userProject1 = UserProject.builder().id(userProjectKey).user(user).project(project).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();
        testEntityManager.persist(userProject1);

        UserProject userProject2 = UserProject.builder().id(userProjectKey2).user(user).project(project2).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();
        testEntityManager.persist(userProject2);

        userProjectRepository.deleteAll();
        assertThat(userProjectRepository.findAll().isEmpty()).isTrue();
    }


}
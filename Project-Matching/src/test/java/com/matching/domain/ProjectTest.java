package com.matching.domain;

import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.repository.ProjectRepository;
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
public class ProjectTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectRepository projectRepository;

    private User user;

    @Before
    public void createUser() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user);
    }

    @Test
    public void projectCreateTest() {
        Project project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                            .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                            .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                            .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project);
        assertThat(projectRepository.getOne(project.getIdx())).isNotNull().isEqualTo(project);
    }

    @Test
    public void projectCreateAndSearchTest() {
        Project project1 = Project.builder().leader(user).title("테스트 프로젝트1").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project1);

        Project project2 = Project.builder().leader(user).title("테스트 프로젝트2").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project2);

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).isNotNull();
        assertThat(projectList.size()).isEqualTo(2);
        assertThat(projectList.get(0)).isEqualTo(project1);
        assertThat(projectList.get(1)).isEqualTo(project2);
    }

    @Test
    public void projectCreateAndDeleteTest() {
        Project project1 = Project.builder().leader(user).title("테스트 프로젝트1").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project1);

        Project project2 = Project.builder().leader(user).title("테스트 프로젝트2").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project2);

        projectRepository.deleteAll();
        assertThat(projectRepository.findAll().isEmpty()).isTrue();
    }
}
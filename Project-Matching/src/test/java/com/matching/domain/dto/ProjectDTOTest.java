package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.UserProjectKey;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ProjectDTOTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserProjectRepository userProjectRepository;

    private User user;

    private Project project;

    private UserProject userProject;

    @Before
    public void createUserAndProjectAndUserProjectKey() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user);

        project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
        testEntityManager.persist(project);

        UserProjectKey userProjectKey = new UserProjectKey(user.getIdx(), project.getIdx());

        userProject = UserProject.builder().id(userProjectKey).user(user).project(project).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();
        testEntityManager.persist(userProject);
    }

    @Test
    public void createProjectDTOTest() {
        ProjectDTO projectDTO = new ProjectDTO(project, userProjectRepository);

        assertThat(projectDTO).isNotNull();
        assertThat(projectDTO.getCreatedDate()).isEqualTo(project.getCreatedDate());
        assertThat(projectDTO.getLeader()).isEqualTo(project.getLeader().getNick());
        assertThat(projectDTO.getLeaderIdx()).isEqualTo(project.getLeader().getIdx());
        assertThat(projectDTO.getLocation()).isEqualTo(project.getLocation().getLocation());
        assertThat(projectDTO.getDesignerRecruits()).isEqualTo(project.getDesignerRecruits());
        assertThat(projectDTO.getDeveloperRecruits()).isEqualTo(project.getDeveloperRecruits());
        assertThat(projectDTO.getEtcRecruits()).isEqualTo(project.getEtcRecruits());
        assertThat(projectDTO.getMarketerRecruits()).isEqualTo(project.getMarketerRecruits());
        assertThat(projectDTO.getPlannerRecruits()).isEqualTo(project.getPlannerRecruits());
        assertThat(projectDTO.getProjectIdx()).isEqualTo(project.getIdx());
        assertThat(projectDTO.getStatus()).isEqualTo(project.getStatus().getStatus());
        assertThat(projectDTO.getSummary()).isEqualTo(project.getSummary());
        assertThat(projectDTO.getTitle()).isEqualTo(project.getTitle());
        assertThat(projectDTO.getContent()).isEqualTo(project.getContent());
    }

}
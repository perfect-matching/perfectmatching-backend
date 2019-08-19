package com.matching.domain.dto;


import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsDTOTest {

    private Project project;

    @Before
    public void createProject() {
        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();

        project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1).build();
    }

    @Test
    public void createProjectsDTOTest() {
        ProjectsDTO projectsDTO = new ProjectsDTO(project);

        assertThat(projectsDTO).isNotNull();
        assertThat(projectsDTO.getCreatedDate()).isEqualTo(project.getCreatedDate());
        assertThat(projectsDTO.getDeadLine()).isEqualTo(project.getDeadline());
        assertThat(projectsDTO.getLeader()).isEqualTo(project.getLeader().getNick());
        assertThat(projectsDTO.getLeaderIdx()).isEqualTo(project.getLeader().getIdx());
        assertThat(projectsDTO.getLeaderImage()).isEqualTo(project.getLeader().getProfileImg());
        assertThat(projectsDTO.getLocation()).isEqualTo(project.getLocation().getLocation());
        assertThat(projectsDTO.getDesignerRecruits()).isEqualTo(project.getDesignerRecruits() > 0);
        assertThat(projectsDTO.getDeveloperRecruits()).isEqualTo(project.getDeveloperRecruits() > 0);
        assertThat(projectsDTO.getEtcRecruits()).isEqualTo(project.getEtcRecruits() > 0);
        assertThat(projectsDTO.getMarketerRecruits()).isEqualTo(project.getMarketerRecruits() > 0);
        assertThat(projectsDTO.getPlannerRecruits()).isEqualTo(project.getPlannerRecruits() > 0);
        assertThat(projectsDTO.getProjectIdx()).isEqualTo(project.getIdx());
        assertThat(projectsDTO.getStatus()).isEqualTo(project.getStatus().getStatus());
        assertThat(projectsDTO.getSummary()).isEqualTo(project.getSummary());
        assertThat(projectsDTO.getTitle()).isEqualTo(project.getTitle());
    }
}
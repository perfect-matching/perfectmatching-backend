package com.matching.domain.dto;

import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.key.UserProjectKey;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDTOTest {

    private UserProject userProject;

    @Before
    public void createUserAndProjectAndUserProject() {
        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        Project project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                .designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();

        UserProjectKey userProjectKey = new UserProjectKey(user.getIdx(), project.getIdx());

        userProject = UserProject.builder().id(userProjectKey).user(user).project(project).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();
    }

    @Test
    public void createMemberDTOTest() {
        MemberDTO memberDTO = new MemberDTO(userProject);

        assertThat(memberDTO).isNotNull();
        assertThat(memberDTO.getMemberIdx()).isEqualTo(userProject.getUser().getIdx());
        assertThat(memberDTO.getMemberNick()).isEqualTo(userProject.getUser().getNick());
        assertThat(memberDTO.getPosition()).isEqualTo(userProject.getPosition().getPosition());
        assertThat(memberDTO.getProjectIdx()).isEqualTo(userProject.getProject().getIdx());
    }

}
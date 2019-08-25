package com.matching.domain.dto;

import com.matching.domain.DoneProject;
import com.matching.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DoneProjectDTOTest {

    private DoneProject doneProject;

    @Before
    public void createUser() {
        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        doneProject = DoneProject.builder().user(user).title("테스트 프로젝트").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
    }

    @Test
    public void doneProjectDTOCreateTest() {
        DoneProjectDTO doneProjectDTO = new DoneProjectDTO(doneProject);

        assertThat(doneProjectDTO).isNotNull();
        assertThat(doneProjectDTO.getContent()).isEqualTo(doneProject.getContent());
        assertThat(doneProjectDTO.getCreatedDate()).isEqualTo(doneProject.getCreatedDate());
        assertThat(doneProjectDTO.getDoneProjectIdx()).isEqualTo(doneProject.getIdx());
        assertThat(doneProjectDTO.getEndDate()).isEqualTo(doneProject.getEndDate());
        assertThat(doneProjectDTO.getModifiedDate()).isEqualTo(doneProject.getModifiedDate());
        assertThat(doneProjectDTO.getProjectIdx()).isEqualTo(doneProject.getProjectIdx());
        assertThat(doneProjectDTO.getSocialUrl()).isEqualTo(doneProject.getSocialUrl());
        assertThat(doneProjectDTO.getStartDate()).isEqualTo(doneProject.getStartDate());
        assertThat(doneProjectDTO.getSummary()).isEqualTo(doneProject.getSummary());
        assertThat(doneProjectDTO.getTitle()).isEqualTo(doneProject.getTitle());
        assertThat(doneProjectDTO.getUserIdx()).isEqualTo(doneProject.getUser().getIdx());
    }

}
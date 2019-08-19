package com.matching.domain.dto;

import com.matching.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileDTOTest {

    private User user;

    @Before
    public void createUser() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
    }

    @Test
    public void createProfileDTOTest() {
        ProfileDTO profileDTO = new ProfileDTO(user);

        assertThat(profileDTO).isNotNull();
        assertThat(profileDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(profileDTO.getUserIdx()).isEqualTo(user.getIdx());
        assertThat(profileDTO.getNickname()).isEqualTo(user.getNick());
        assertThat(profileDTO.getSummary()).isEqualTo(user.getDescription());
    }
}
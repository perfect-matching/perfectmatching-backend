package com.matching.domain.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest {

    @Test
    public void userDTOCreateTest() {
        UserDTO userDTO = new UserDTO();

        assertThat(userDTO).isNotNull();
    }
}
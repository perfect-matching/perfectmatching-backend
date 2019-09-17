package com.matching.domain.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectApplyDTOTest {

    @Test
    public void createProjectApplyDTOTest() {
        ProjectApplyDTO projectApplyDTO = new ProjectApplyDTO();

        assertThat(projectApplyDTO).isNotNull();
    }

}
package com.matching.controller;

import com.matching.domain.DoneProject;
import com.matching.domain.User;
import com.matching.repository.DoneProjectRepository;
import com.matching.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoneProjectControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    private DoneProject doneProject;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();

        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        userRepository.save(user);

        doneProject = DoneProject.builder().user(user).title("테스트 프로젝트").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();

        doneProjectRepository.save(doneProject);
    }

    @Test
    public void getDoneProjectTest() throws Exception {
        mockMvc.perform(get("/api/doneproject/" + doneProject.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/doneproject/" + doneProject.getIdx()))
                .andExpect(status().isOk());
    }

    @Test
    public void getDoneProjectUsedSkillsTest() throws Exception {
        mockMvc.perform(get("/api/doneproject/" + doneProject.getIdx() + "/usedskills").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/doneproject/" + doneProject.getIdx() + "/usedskills"))
                .andExpect(status().isOk());
    }

}
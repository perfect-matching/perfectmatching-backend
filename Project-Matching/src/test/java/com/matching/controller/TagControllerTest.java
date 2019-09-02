package com.matching.controller;

import com.matching.domain.*;
import com.matching.repository.*;
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
public class TagControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UsedSkillRepository usedSkillRepository;

    private Tag tag;

    private UsedSkill usedSkill;

    private UserSkill userSkill;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();

        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        userRepository.save(user);

        DoneProject doneProject = DoneProject.builder().user(user).title("테스트 프로젝트").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();

        doneProjectRepository.save(doneProject);

        tag = Tag.builder().text("테스트 태그1").build();
        tagRepository.save(tag);

        userSkill = UserSkill.builder().text("테스트 유저 스킬").user(user).build();
        userSkillRepository.save(userSkill);

        usedSkill = UsedSkill.builder().text("테스트 Done 프로젝트 스킬").doneProject(doneProject).build();
        usedSkillRepository.save(usedSkill);
    }

    @Test
    public void getTagsTest() throws Exception {
        mockMvc.perform(get("/api/tags").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/tags"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTagTest() throws Exception {
        mockMvc.perform(get("/api/tag/" + tag.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/tag/" + tag.getIdx()))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserSkillsTest() throws Exception {
        mockMvc.perform(get("/api/userskills").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/userskills"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserSkillTest() throws Exception {
        mockMvc.perform(get("/api/userskill/" + userSkill.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/userskill/" + userSkill.getIdx()))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsedSkillsTest() throws Exception {
        mockMvc.perform(get("/api/usedskills").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/usedskills"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsedSkillTest() throws Exception {
        mockMvc.perform(get("/api/usedskill/" + usedSkill.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/usedskill/" + usedSkill.getIdx()))
                .andExpect(status().isOk());
    }
}
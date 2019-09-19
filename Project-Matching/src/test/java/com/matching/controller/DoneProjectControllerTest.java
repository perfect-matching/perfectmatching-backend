package com.matching.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matching.config.auth.SecurityConstants;
import com.matching.domain.DoneProject;
import com.matching.domain.UsedSkill;
import com.matching.domain.User;
import com.matching.domain.dto.DoneProjectDTO;
import com.matching.repository.DoneProjectRepository;
import com.matching.repository.UserRepository;
import com.matching.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private ObjectMapper objectMapper;

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    @Autowired
    private UserService userService;

    private DoneProject doneProject;

    private UserDetails userDetails;

    private String token;

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

        userDetails = userService.loadUserByUsername(user.getEmail());

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET.getBytes())
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject("Perfect-Matching JWT Token")
                .claim("idx", userRepository.findByEmail(user.getEmail()).getIdx())
                .claim("email", user.getEmail())
                .claim("nickname", user.getNick())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .claim("role", roles)
                .compact();
    }

    @Test
    public void getDoneProjectTest() throws Exception {
        mockMvc.perform(get("/api/doneproject/" + doneProject.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void getDoneProjectUsedSkillsTest() throws Exception {
        mockMvc.perform(get("/api/doneproject/" + doneProject.getIdx() + "/usedskills").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void postDoneProjectTest() throws Exception {
        Set<UsedSkill> usedSkills = new HashSet<>();
        usedSkills.add(UsedSkill.builder().text("Java").build());
        usedSkills.add(UsedSkill.builder().text("Spring").build());

        DoneProjectDTO doneProjectDTO = DoneProjectDTO.builder().title("돈 프로젝트 테스트 타이틀").summary("테스트").content("타이틀")
                .tags(usedSkills).startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).socialUrl("Github").build();

        mockMvc.perform(post("/api/doneproject").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(doneProjectDTO)).with(user(userDetails)))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isCreated());

    }

    @Test
    public void putDoneProjectTest() throws Exception {
        Set<UsedSkill> usedSkills = new HashSet<>();
        usedSkills.add(UsedSkill.builder().text("Java").build());
        usedSkills.add(UsedSkill.builder().text("Spring").build());

        DoneProjectDTO doneProjectDTO = DoneProjectDTO.builder().title("돈 프로젝트 테스트 타이틀").summary("테스트").content("타이틀")
                .tags(usedSkills).startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).socialUrl("Github").build();

        mockMvc.perform(post("/api/doneproject").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(doneProjectDTO)).with(user(userDetails)))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isCreated());

        doneProjectDTO.setTitle("수정된 타이틀");
        doneProjectDTO.setContent("수정된 내용");
        doneProjectDTO.setSummary("수정된 요약");
        doneProjectDTO.setSocialUrl("수정된 깃허브");

        usedSkills.remove(UsedSkill.builder().text("Spring").build());
        usedSkills.add(UsedSkill.builder().text("Python").build());

        doneProjectDTO.setTags(usedSkills);

        Long doneProjectIdx = doneProjectRepository.findByTitle("돈 프로젝트 테스트 타이틀").getIdx();

        mockMvc.perform(put("/api/doneproject/" + doneProjectIdx).header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(doneProjectDTO)).with(user(userDetails)))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDoneProjectTest() throws Exception {
        Set<UsedSkill> usedSkills = new HashSet<>();
        usedSkills.add(UsedSkill.builder().text("Java").build());
        usedSkills.add(UsedSkill.builder().text("Spring").build());

        DoneProjectDTO doneProjectDTO = DoneProjectDTO.builder().title("돈 프로젝트 테스트 타이틀").summary("테스트").content("타이틀")
                .tags(usedSkills).startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).socialUrl("Github").build();

        mockMvc.perform(post("/api/doneproject").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(doneProjectDTO)).with(user(userDetails)))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isCreated());

        Long doneProjectIdx = doneProjectRepository.findByTitle("돈 프로젝트 테스트 타이틀").getIdx();

        mockMvc.perform(delete("/api/doneproject/" + doneProjectIdx).header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE).with(user(userDetails)))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

}
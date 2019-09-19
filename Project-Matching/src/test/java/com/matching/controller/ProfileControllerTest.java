package com.matching.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matching.config.auth.SecurityConstants;
import com.matching.domain.User;
import com.matching.domain.UserSkill;
import com.matching.domain.dto.ProfileDTO;
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
public class ProfileControllerTest {

    public static final String TEST_EMAIL = "Test_User@gmail.com";
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Autowired
    private UserService userService;

    private UserDetails userDetails;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();

        user = User.builder().nick("Test User").email(TEST_EMAIL).password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        userRepository.save(user);

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
    public void getProfileTest() throws Exception {
        mockMvc.perform(get("/api/profile/" + user.getIdx()).with(user(TEST_EMAIL)
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProfileSkillsTest() throws Exception {
        mockMvc.perform(get("/api/profile/" + user.getIdx() + "/skills").with(user(TEST_EMAIL)
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/profile/" + user.getIdx() + "/skills"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProfileMyProjectsTest() throws Exception {
        mockMvc.perform(get("/api/profile/" + user.getIdx() + "/myprojects").with(user(TEST_EMAIL)
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProfileProjectsTest() throws Exception {
        mockMvc.perform(get("/api/profile/" + user.getIdx() + "/projects").with(user(TEST_EMAIL)
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProfileDoneProjectsTest() throws Exception {
        mockMvc.perform(get("/api/profile/" + user.getIdx() + "/doneprojects").with(user(TEST_EMAIL)
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProfileApplyProjectsTest() throws Exception {
        mockMvc.perform(get("/api/profile/" + user.getIdx() + "/applyprojects").with(user(TEST_EMAIL)
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void putProfileTest() throws Exception {
        User user = userRepository.findByEmail(TEST_EMAIL);

        Set<UserSkill> set = new HashSet<>();

        set.add(UserSkill.builder().text("Java").build());
        set.add(UserSkill.builder().text("Node.js").build());

        ProfileDTO profileDTO = ProfileDTO.builder().nickname("테스트 닉네임").summary("테스트 소개").socialUrl("테스트 소셜").profileImage("테스트 이미지")
                .investTime(5).userSkills(set).email("testuser@email.com").build();

        mockMvc.perform(put("/api/profile/" + user.getIdx()).header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(profileDTO)).with(user(userDetails)))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }


}
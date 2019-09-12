package com.matching.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matching.domain.UserSkill;
import com.matching.domain.dto.UserDTO;
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

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void postUserTest() throws Exception {
        Set<UserSkill> userSkills = new HashSet<>();

        userSkills.add(UserSkill.builder().text("테스트 유저 스길 1").build());
        userSkills.add(UserSkill.builder().text("유저 스길 2").build());

        UserDTO userDTO = UserDTO.builder().email("simpleUser@gamil.com").password("1108sun@#!").confirmPassword("1108sun@#!")
                .nickname("simpleUser").summary("이러이러한 사람입니다.").socialUrl("https://github.com").investTime(3)
                .userSkills(userSkills).build();

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                        .andExpect(status().isCreated());
    }

    @Test
    public void nickCheckTest() throws Exception {
        mockMvc.perform(post("/api/register/nickcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("SimpleUser2222")))
                .andExpect(status().isOk());
    }

    @Test
    public void emailCheckTest() throws Exception {
        mockMvc.perform(post("/api/register/emailcheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("SimpleUser2222@gmail.com")))
                .andExpect(status().isOk());
    }

}
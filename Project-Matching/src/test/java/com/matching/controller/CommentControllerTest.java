package com.matching.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matching.config.auth.SecurityConstants;
import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.dto.CommentDTO;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserRepository;
import com.matching.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@SpringBootTest
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    private Comment comment;

    private String token;

    private UserDetails userDetails;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password(passwordEncoder.encode("test_password"))
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        userRepository.save(user) ;

        Project project = Project.builder().leader(user).title("테스트 프로젝트1234").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();

        projectRepository.save(project);


        comment = Comment.builder().project(project).writer(user).content("테스트 댓글").createdDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

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

    @After
    public void setupDBClear() {
        User user = userRepository.findByEmail(userDetails.getUsername());
        userRepository.deleteById(user.getIdx());
    }

    @Test
    public void getCommentTest() throws Exception {
        mockMvc.perform(get("/api/comment/" + comment.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());

    }

    @Test
    public void postCommentTest() throws Exception {

        Long projectIdx = projectRepository.findByTitle("테스트 프로젝트1234").getIdx();

        CommentDTO commentDTO = CommentDTO.builder().content("테스트 코드에서 작성하는 댓글").projectIdx(projectIdx).build();

        mockMvc.perform(post("/api/comment").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentDTO)).with(user("Test_User@gmail.com").password("test_password")))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isCreated());
    }

    @Test
    public void putCommentTest() throws Exception {
        Long projectIdx = projectRepository.findByTitle("테스트 프로젝트1234").getIdx();

        CommentDTO commentDTO = CommentDTO.builder().content("테스트 코드에서 작성하는 댓글").projectIdx(projectIdx).build();

        mockMvc.perform(post("/api/comment").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentDTO)).with(user("Test_User@gmail.com").password("test_password")))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isCreated());

        commentDTO.setContent("테스크 코드에서 수정된 댓글...");

        mockMvc.perform(put("/api/comment/" + commentRepository.findByContent("테스트 코드에서 작성하는 댓글").getIdx())
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentDTO)).with(user("Test_User@gmail.com").password("test_password")))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentTest() throws Exception {
        Long projectIdx = projectRepository.findByTitle("테스트 프로젝트1234").getIdx();

        CommentDTO commentDTO = CommentDTO.builder().content("테스트 코드에서 작성하는 댓글").projectIdx(projectIdx).build();

        mockMvc.perform(post("/api/comment").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentDTO)).with(user("Test_User@gmail.com").password("test_password")))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/comment/" + commentRepository.findByContent("테스트 코드에서 작성하는 댓글").getIdx())
                .header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentDTO)).with(user("Test_User@gmail.com").password("test_password")))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

}
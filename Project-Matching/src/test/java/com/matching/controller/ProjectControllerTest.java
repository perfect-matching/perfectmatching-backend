package com.matching.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matching.config.auth.SecurityConstants;
import com.matching.domain.*;
import com.matching.domain.dto.ProjectDTO;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.domain.key.UserProjectKey;
import com.matching.repository.*;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class ProjectControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private Project project;

    private UserDetails userDetails;

    private String token;

    @Before
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password(passwordEncoder.encode("test_password"))
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();

        userRepository.save(user);

        project = Project.builder().leader(user).title("테스트프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();

        projectRepository.save(project);

        UserProjectKey userProjectKey = new UserProjectKey(user.getIdx(), project.getIdx());

        UserProject userProject = UserProject.builder().id(userProjectKey).user(user).project(project).simpleProfile("자기소개")
                .position(PositionType.getRandomPositionType()).status(UserProjectStatus.getRandomUserProjectStatus())
                .build();

        userProjectRepository.save(userProject);

        Comment comment = Comment.builder().project(project).writer(user).content("테스트 댓글").createdDate(LocalDateTime.now())
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

    @Test
    public void getProjectsJsonViewTest() throws Exception {
        mockMvc.perform(get("/api/projects"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectsJsonViewTes2t() throws Exception {
        mockMvc.perform(get("/api/projects?location=BUSAN"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/projects"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectJsonViewTest() throws Exception {
        mockMvc.perform(get("/api/project/" + project.getIdx()).with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/project/" + project.getIdx()))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectCommentsTest() throws Exception {
        mockMvc.perform(get("/api/project/" + project.getIdx() + "/comments").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/project/" + project.getIdx() + "/comments"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectMembersTest() throws Exception {
        mockMvc.perform(get("/api/project/" + project.getIdx() + "/members").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/project/" + project.getIdx() + "/members"))
                .andExpect(status().isOk());
    }

    @Test
    public void getProjectTagsTest() throws Exception {
        mockMvc.perform(get("/api/project/" + project.getIdx() + "/tags").with(user("Test_User@gmail.com")
                .password("test_password")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(redirectedUrl("/api/project/" + project.getIdx() + "/tags"))
                .andExpect(status().isOk());
    }

    @Test
    public void postProjectTest() throws Exception {

        Set<Tag> tag = new HashSet<>();

        tag.add(Tag.builder().text("테스트 코드 태그 1").build());
        tag.add(Tag.builder().text("테스트 코드 태그 2").build());

        ProjectDTO projectdto = ProjectDTO.builder().title("테스트 타이틀 12312321").location("서울").summary("요로요러한 프로젝트")
                .designerRecruits(1).developerRecruits(1).plannerRecruits(1).marketerRecruits(1).etcRecruits(1).tags(tag).build();

        mockMvc.perform(post("/api/project").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(projectdto)).with(user(userDetails)))
                .andExpect(status().isCreated());
    }

    @Test
    public void putProjectTest() throws Exception {
        Set<Tag> tag = new HashSet<>();

        tag.add(Tag.builder().text("테스트 코드 태그 1").build());
        tag.add(Tag.builder().text("테스트 코드 태그 2").build());

        ProjectDTO projectdto = ProjectDTO.builder().title("테스트 타이틀 12312321").location("서울").summary("요로요러한 프로젝트")
                .designerRecruits(1).developerRecruits(1).plannerRecruits(1).marketerRecruits(1).etcRecruits(1).tags(tag).build();

        mockMvc.perform(post("/api/project").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(projectdto)).with(user(userDetails)))
                .andExpect(status().isCreated());

        Set<Tag> tag2 = new HashSet<>();

        tag2.add(Tag.builder().text("테스트 코드 태그 1(수정)").build());
        tag2.add(Tag.builder().text("테스트 코드 태그 2(수정)").build());

        projectdto.setTitle("수정된 프로젝트");
        projectdto.setTags(tag2);

        mockMvc.perform(put("/api/project/" + projectRepository.findByTitle("테스트 타이틀 12312321").getIdx()).header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(projectdto)).with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProjectTest() throws Exception {
        Set<Tag> tag = new HashSet<>();

        tag.add(Tag.builder().text("테스트 코드 태그 1").build());
        tag.add(Tag.builder().text("테스트 코드 태그 2").build());

        ProjectDTO projectdto = ProjectDTO.builder().title("테스트 타이틀 12312321").location("서울").summary("요로요러한 프로젝트")
                .designerRecruits(1).developerRecruits(1).plannerRecruits(1).marketerRecruits(1).etcRecruits(1).tags(tag).build();

        mockMvc.perform(post("/api/project").header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(projectdto)).with(user(userDetails)))
                .andExpect(status().isCreated());

        Comment comment = Comment.builder().writer(userRepository.findByEmail("Test_User@gmail.com")).project(projectRepository.findByTitle("테스트 타이틀 12312321"))
                .content("테스트 댓글").createdDate(LocalDateTime.now()).build();

        commentRepository.save(comment);

        mockMvc.perform(delete("/api/project/" + projectRepository.findByTitle("테스트 타이틀 12312321").getIdx()).header(SecurityConstants.TOKEN_HEADER, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(projectdto)).with(user(userDetails)))
                .andExpect(status().isOk());
    }


}
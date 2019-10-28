package com.matching.controller;

import com.matching.config.auth.SecurityConstants;
import com.matching.domain.User;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "donghun-dev.kro.kr")
public class FileControllerTest {

    public static final String TEST_EMAIL = "TestUser@email.com";
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private String token;

    private UserDetails userDetails;

    private String DEFAULT_IMAGE_URI;

    @Before
    public void setMockMvc() {
        DEFAULT_IMAGE_URI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path("USER_DEFAULT_PROFILE_IMG.png")
                .toUriString();

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .alwaysDo(print())
                .build();

        User user = User.builder().nick("Test User").email(TEST_EMAIL).password("TestPassword")
                .profileImg(DEFAULT_IMAGE_URI).description("test desc..").createdDate(LocalDateTime.now())
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

    @After
    public void setDB() {
        User user = userRepository.findByEmail(userDetails.getUsername());
        userRepository.deleteById(user.getIdx());
    }

    @Test
    public void getImageTest() throws Exception {
        mockMvc.perform(get("/api/image/USER_DEFAULT_PROFILE_IMG.png"))
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());
    }

    @Test
    public void getImageTest2() throws Exception {
        mockMvc.perform(get("/api/image/NOT_EXIST.png"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putAndDeleteTest() throws Exception {
        final MockMultipartFile image = new MockMultipartFile("file", "test.png", "image/png", "TEST".getBytes());

        String prevUrl = userRepository.findByEmail(TEST_EMAIL).getProfileImg();
        assertThat(prevUrl).isEqualTo(prevUrl);

        MockHttpServletRequestBuilder builder = multipart("/api/image").file(image)
                .with(user(userDetails)).header(SecurityConstants.TOKEN_HEADER, token);
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mockMvc.perform(builder)
                .andExpect(header().exists("Location"))
                .andExpect(header().exists("Link"))
                .andExpect(status().isOk());

        String modifyUrl = userRepository.findByEmail(TEST_EMAIL).getProfileImg();
        assertThat(prevUrl).isNotEqualTo(modifyUrl);

        mockMvc.perform(delete("/api/image").with(user(userDetails)).header(SecurityConstants.TOKEN_HEADER, token))
                .andExpect(status().isOk());

        String deleteUrl = userRepository.findByEmail(TEST_EMAIL).getProfileImg();
        assertThat(deleteUrl).isEqualTo(DEFAULT_IMAGE_URI);

    }
}

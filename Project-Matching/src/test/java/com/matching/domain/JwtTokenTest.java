package com.matching.domain;

import com.matching.repository.JwtTokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class JwtTokenTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    private User user;

    @Before
    public void createUser() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);
    }

    @Test
    public void jwtTokenCreateTest() {
        JwtToken jwtToken = JwtToken.builder().token(UUID.randomUUID().toString()).build();
        testEntityManager.persist(jwtToken);

        assertThat(jwtTokenRepository.getOne(jwtToken.getIdx())).isNotNull().isEqualTo(jwtToken);
    }

    @Test
    public void jwtTokenCreateAndSearchTest() {
        JwtToken jwtToken1 = JwtToken.builder().token(UUID.randomUUID().toString()).build();
        testEntityManager.persist(jwtToken1);

        JwtToken jwtToken2 = JwtToken.builder().token(UUID.randomUUID().toString()).build();
        testEntityManager.persist(jwtToken2);

        List<JwtToken> jwtTokenList = jwtTokenRepository.findAll();

        assertThat(jwtTokenList).isNotNull();
        assertThat(jwtTokenList.size()).isEqualTo(2);
        assertThat(jwtTokenList.get(0)).isNotNull().isEqualTo(jwtToken1);
        assertThat(jwtTokenList.get(1)).isNotNull().isEqualTo(jwtToken2);
    }

    @Test
    public void jwtTokenCreateAndDeleteTest() {
        JwtToken jwtToken1 = JwtToken.builder().token(UUID.randomUUID().toString()).build();
        testEntityManager.persist(jwtToken1);

        JwtToken jwtToken2 = JwtToken.builder().token(UUID.randomUUID().toString()).build();
        testEntityManager.persist(jwtToken2);

        jwtTokenRepository.deleteAll();

        assertThat(jwtTokenRepository.findAll().isEmpty()).isTrue();
    }

}
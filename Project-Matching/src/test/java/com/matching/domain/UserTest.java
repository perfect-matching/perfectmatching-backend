package com.matching.domain;

import com.matching.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class UserTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userCreateTest() {
        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                        .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user);
        assertThat(userRepository.getOne(user.getIdx())).isNotNull().isEqualTo(user);
    }

    @Test
    public void userCreateAndSearchTest() {
        User user1 = User.builder().nick("Test User1").email("Test_User1@gmail.com").password("test_password1")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user1);

        User user2 = User.builder().nick("Test User2").email("Test_User2@gmail.com").password("test_password2")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user2);

        User user3 = User.builder().nick("Test User3").email("Test_User3@gmail.com").password("test_password3")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user3);

        List<User> userList = userRepository.findAll();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(3);
        assertThat(userList.get(0)).isEqualTo(user1);
        assertThat(userList.get(1)).isEqualTo(user2);
        assertThat(userList.get(2)).isEqualTo(user3);
    }

    @Test
    public void userCreateAndDeleteTest() {
        User user1 = User.builder().nick("Test User1").email("Test_User1@gmail.com").password("test_password1")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user1);

        User user2 = User.builder().nick("Test User2").email("Test_User2@gmail.com").password("test_password2")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now()).build();
        testEntityManager.persist(user2);

        userRepository.deleteAll();
        assertThat(userRepository.findAll().isEmpty()).isTrue();
    }

}
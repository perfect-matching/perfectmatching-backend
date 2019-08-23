package com.matching.domain;

import com.matching.repository.UserSkillRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class UserSkillTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserSkillRepository userSkillRepository;

    private User user;

    @Before
    public void createUser() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);
    }

    @Test
    public void userSkillCreateTest() {
        UserSkill userSkill = UserSkill.builder().user(user).text("테스트 유저스킬").build();
        testEntityManager.persist(userSkill);
        assertThat(userSkillRepository.getOne(userSkill.getIdx())).isNotNull().isEqualTo(userSkill);
    }

    @Test
    public void userSkillCreateAndSearch() {
        UserSkill userSkill1 = UserSkill.builder().user(user).text("테스트 유저스킬1").build();
        testEntityManager.persist(userSkill1);

        UserSkill userSkill2 = UserSkill.builder().user(user).text("테스트 유저스킬2").build();
        testEntityManager.persist(userSkill2);

        List<UserSkill> list = userSkillRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isNotNull().isEqualTo(userSkill1);
        assertThat(list.get(1)).isNotNull().isEqualTo(userSkill2);
    }

    @Test
    public void userSkillCreateAndDelete() {
        UserSkill userSkill1 = UserSkill.builder().user(user).text("테스트 유저스킬1").build();
        testEntityManager.persist(userSkill1);

        UserSkill userSkill2 = UserSkill.builder().user(user).text("테스트 유저스킬2").build();
        testEntityManager.persist(userSkill2);

        userSkillRepository.deleteAll();

        assertThat(userSkillRepository.findAll().isEmpty()).isTrue();
    }
}
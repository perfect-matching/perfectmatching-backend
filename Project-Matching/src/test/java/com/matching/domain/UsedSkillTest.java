package com.matching.domain;

import com.matching.repository.UsedSkillRepository;
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
public class UsedSkillTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UsedSkillRepository usedSkillRepository;

    private DoneProject doneProject;

    @Before
    public void createUserAndDoneProject() {
        User user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);

        doneProject = DoneProject.builder().user(user).title("테스트 프로젝트").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
        testEntityManager.persist(doneProject);
    }

    @Test
    public void usedSkillCreateTest() {
        UsedSkill usedSkill = UsedSkill.builder().doneProject(doneProject).text("테스트 스킬").build();
        testEntityManager.persist(usedSkill);
        assertThat(usedSkillRepository.getOne(usedSkill.getIdx())).isNotNull().isEqualTo(usedSkill);
    }

    @Test
    public void usedSkillCreateAndSearch() {
        UsedSkill usedSkill1 = UsedSkill.builder().doneProject(doneProject).text("테스트 스킬").build();
        testEntityManager.persist(usedSkill1);

        UsedSkill usedSkill2 = UsedSkill.builder().doneProject(doneProject).text("테스트 스킬").build();
        testEntityManager.persist(usedSkill2);

        List<UsedSkill> list = usedSkillRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isNotNull().isEqualTo(usedSkill1);
        assertThat(list.get(1)).isNotNull().isEqualTo(usedSkill2);
    }

    @Test
    public void usedSkillCreateAndDelete() {
        UsedSkill usedSkill1 = UsedSkill.builder().doneProject(doneProject).text("테스트 스킬").build();
        testEntityManager.persist(usedSkill1);

        UsedSkill usedSkill2 = UsedSkill.builder().doneProject(doneProject).text("테스트 스킬").build();
        testEntityManager.persist(usedSkill2);

        usedSkillRepository.deleteAll();

        assertThat(usedSkillRepository.findAll().isEmpty()).isTrue();
    }
}
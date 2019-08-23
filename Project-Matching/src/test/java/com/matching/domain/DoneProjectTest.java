package com.matching.domain;

import com.matching.repository.DoneProjectRepository;
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
public class DoneProjectTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    private User user;

    @Before
    public void createUser() {
        user = User.builder().nick("Test User").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);
    }

    @Test
    public void doneProjectCreateTest() {
        DoneProject doneProject = DoneProject.builder().user(user).title("테스트 프로젝트").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
        testEntityManager.persist(doneProject);
        assertThat(doneProjectRepository.getOne(doneProject.getIdx())).isNotNull().isEqualTo(doneProject);
    }

    @Test
    public void doneProjectCreateAndSearchTest() {
        DoneProject doneProject1 = DoneProject.builder().user(user).title("테스트 프로젝트1").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
        testEntityManager.persist(doneProject1);

        DoneProject doneProject2 = DoneProject.builder().user(user).title("테스트 프로젝트2").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
        testEntityManager.persist(doneProject2);

        List<DoneProject> list = doneProjectRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isNotNull().isEqualTo(doneProject1);
        assertThat(list.get(1)).isNotNull().isEqualTo(doneProject2);
    }

    @Test
    public void doneProjectCreateAndDelete() {
        DoneProject doneProject1 = DoneProject.builder().user(user).title("테스트 프로젝트1").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
        testEntityManager.persist(doneProject1);

        DoneProject doneProject2 = DoneProject.builder().user(user).title("테스트 프로젝트2").summary("테스트 프로젝트 입니다.")
                .content("테스트 내용").createdDate(LocalDateTime.now()).startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()).build();
        testEntityManager.persist(doneProject2);

        doneProjectRepository.deleteAll();
        assertThat(doneProjectRepository.findAll().isEmpty()).isTrue();
    }

}
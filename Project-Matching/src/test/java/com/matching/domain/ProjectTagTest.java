package com.matching.domain;

import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.key.ProjectTagKey;
import com.matching.repository.ProjectTagRepository;
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
public class ProjectTagTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProjectTagRepository projectTagRepository;

    private Project project;

    private Tag tag1;

    private Tag tag2;

    private ProjectTagKey projectTagKey1;

    private ProjectTagKey projectTagKey2;

    @Before
    public void createUserAndProjectAndTagAndProjectTagKey() {
        User user = User.builder().nick("Test User1").email("Test_User@gmail.com").password("test_password")
                .profileImg("image..").description("test desc..").createdDate(LocalDateTime.now())
                .investTime(4).socialUrl("https://github.com/testUser").build();
        testEntityManager.persist(user);

        project = Project.builder().leader(user).title("테스트 프로젝트").content("테스트 생성").summary("테스트 프로젝트")
                .status(ProjectStatus.getRandomProjectStatus()).location(LocationType.getRandomLocationType())
                .createdDate(LocalDateTime.now()).designerRecruits(1).developerRecruits(1).etcRecruits(1).marketerRecruits(1).plannerRecruits(1)
                .socialUrl("https://github.com/testUser/testProject").build();
        testEntityManager.persist(project);

        tag1 = Tag.builder().text("테스트 태그1").build();
        testEntityManager.persist(tag1);

        tag2 = Tag.builder().text("테스트 태그2").build();
        testEntityManager.persist(tag2);

        projectTagKey1 = new ProjectTagKey(project.getIdx(), tag1.getIdx());
        projectTagKey2 = new ProjectTagKey(project.getIdx(), tag2.getIdx());
    }

    @Test
    public void projectTagCreateTest() {
        ProjectTag projectTag = ProjectTag.builder().id(projectTagKey1).project(project).tag(tag1).build();
        testEntityManager.persist(projectTag);

        assertThat(projectTagRepository.getOne(projectTag.getId())).isNotNull().isEqualTo(projectTag);
    }

    @Test
    public void projectTagCreateAndSearchTest() {
        ProjectTag projectTag1 = ProjectTag.builder().id(projectTagKey1).project(project).tag(tag1).build();
        testEntityManager.persist(projectTag1);

        ProjectTag projectTag2 = ProjectTag.builder().id(projectTagKey2).project(project).tag(tag2).build();
        testEntityManager.persist(projectTag2);

        List<ProjectTag> list = projectTagRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isNotNull().isEqualTo(projectTag1);
        assertThat(list.get(1)).isNotNull().isEqualTo(projectTag2);
    }

    @Test
    public void projectTagCreateAndDeleteTest() {
        ProjectTag projectTag1 = ProjectTag.builder().id(projectTagKey1).project(project).tag(tag1).build();
        testEntityManager.persist(projectTag1);

        ProjectTag projectTag2 = ProjectTag.builder().id(projectTagKey2).project(project).tag(tag2).build();
        testEntityManager.persist(projectTag2);

        projectTagRepository.deleteAll();

        assertThat(projectTagRepository.findAll().isEmpty()).isTrue();
    }
}
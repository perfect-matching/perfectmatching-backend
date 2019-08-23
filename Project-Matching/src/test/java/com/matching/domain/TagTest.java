package com.matching.domain;

import com.matching.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class TagTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void tagCreateTest() {
        Tag tag = Tag.builder().text("테스트 태그").build();
        testEntityManager.persist(tag);
        assertThat(tagRepository.getOne(tag.getIdx())).isNotNull().isEqualTo(tag);
    }

    @Test
    public void tagCreateAndSearchTest() {
        Tag tag1 = Tag.builder().text("테스트 태그1").build();
        testEntityManager.persist(tag1);

        Tag tag2 = Tag.builder().text("테스트 태그2").build();
        testEntityManager.persist(tag2);

        Tag tag3 = Tag.builder().text("테스트 태그3").build();
        testEntityManager.persist(tag3);

        List<Tag> list = tagRepository.findAll();

        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isNotNull().isEqualTo(tag1);
        assertThat(list.get(1)).isNotNull().isEqualTo(tag2);
        assertThat(list.get(2)).isNotNull().isEqualTo(tag3);
    }

    @Test
    public void tagCreateAndDeleteTest() {
        Tag tag1 = Tag.builder().text("테스트 태그1").build();
        testEntityManager.persist(tag1);

        Tag tag2 = Tag.builder().text("테스트 태그2").build();
        testEntityManager.persist(tag2);

        Tag tag3 = Tag.builder().text("테스트 태그3").build();
        testEntityManager.persist(tag3);

        tagRepository.deleteAll();

        assertThat(tagRepository.findAll().isEmpty()).isTrue();
    }
}
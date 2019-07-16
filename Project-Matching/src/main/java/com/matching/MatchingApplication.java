package com.matching;

import com.matching.domain.*;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class MatchingApplication implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    CommentRepository commentRepository;

    public static void main(String[] args) {
        SpringApplication.run(MatchingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User user1 = User.builder().createdDate(LocalDateTime.now()).email("test1@test.com").
                password("123456").nick("별명1").build();

        User user2 = User.builder().createdDate(LocalDateTime.now()).email("test2@test.com").
                password("123456").nick("별명2").build();

        userRepository.save(user1);
        userRepository.save(user2);

        Project project1 = Project.builder().content("내용1").title("제목1").endDate(LocalDateTime.now()).
                startDate(LocalDateTime.now()).deadline(LocalDateTime.now()).location("부산").
                createdDate(LocalDateTime.now()).status("모집").leader(user1).build();

        Project project2 = Project.builder().content("내용2").title("제목2").endDate(LocalDateTime.now()).
                startDate(LocalDateTime.now()).deadline(LocalDateTime.now()).location("부산").
                createdDate(LocalDateTime.now()).status("모집").leader(user1).build();

        projectRepository.save(project1);
        projectRepository.save(project2);

        UserProjectKey key1 = new UserProjectKey(user1.getIdx(), project1.getIdx());
        UserProjectKey key2 = new UserProjectKey(user1.getIdx(), project2.getIdx());
        UserProjectKey key3 = new UserProjectKey(user2.getIdx(), project2.getIdx());

        UserProject userProject1 = UserProject.builder().id(key1).user(user1).project(project1).
                position("개발자").status("매칭중").build();

        UserProject userProject2 = UserProject.builder().id(key2).user(user1).project(project2).
                position("디자이너").status("프로젝트참여").build();

        UserProject userProject3 = UserProject.builder().id(key3).user(user2).project(project2).
                position("디자이너").status("매칭중").build();

        userProjectRepository.save(userProject1);
        userProjectRepository.save(userProject2);
        userProjectRepository.save(userProject3);

        Comment comment1 = Comment.builder().content("테스트 댓글1").createdDate(LocalDateTime.now()).project(project1)
                .writer(user1).build();

        Comment comment2 = Comment.builder().content("테스트 댓글2").createdDate(LocalDateTime.now()).project(project1)
                .writer(user2).build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);


    }
}

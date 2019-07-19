package com.matching;

import com.matching.domain.*;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user1 = User.builder().createdDate(LocalDateTime.now()).email("test1@test.com").
                password("123456").nick("별명1").build();

        User user2 = User.builder().createdDate(LocalDateTime.now()).email("test2@test.com").
                password("123456").nick("별명2").build();

        User user3 = User.builder().createdDate(LocalDateTime.now()).email("test3@test.com").
                password("123456").nick("별명3").build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        Project project1 = Project.builder().content("내용1").title("제목1").endDate(LocalDateTime.now()).
                startDate(LocalDateTime.now()).deadline(LocalDateTime.now()).location("부산").
                createdDate(LocalDateTime.now()).status("모집").build();

        Project project2 = Project.builder().content("내용2").title("제목2").endDate(LocalDateTime.now()).
                startDate(LocalDateTime.now()).deadline(LocalDateTime.now()).location("서울").
                createdDate(LocalDateTime.now()).status("모집").build();

        Project project3 = Project.builder().content("내용3").title("제목3").endDate(LocalDateTime.now()).
                startDate(LocalDateTime.now()).deadline(LocalDateTime.now()).location("대구").
                createdDate(LocalDateTime.now()).status("모집").build();


        user1.addProject(project1);
        projectRepository.save(project1);

        user1.addProject(project2);
        projectRepository.save(project2);

        user2.addProject(project3);
        projectRepository.save(project3);

        UserProjectKey key1 = new UserProjectKey(user1.getIdx(), project1.getIdx());
        UserProjectKey key2 = new UserProjectKey(user1.getIdx(), project2.getIdx());
        UserProjectKey key3 = new UserProjectKey(user2.getIdx(), project2.getIdx());
        UserProjectKey key4 = new UserProjectKey(user2.getIdx(), project3.getIdx());
        UserProjectKey key5 = new UserProjectKey(user3.getIdx(), project1.getIdx());
        UserProjectKey key6 = new UserProjectKey(user3.getIdx(), project3.getIdx());

        UserProject userProject1 = UserProject.builder().id(key1).position("개발자").status("참여").build();
        UserProject userProject2 = UserProject.builder().id(key2).position("개발자").status("참여").build();
        UserProject userProject3 = UserProject.builder().id(key3).position("디자이너").status("매칭중").build();
        UserProject userProject4 = UserProject.builder().id(key4).position("디자이너").status("참여").build();
        UserProject userProject5 = UserProject.builder().id(key5).position("기획자").status("매칭중").build();
        UserProject userProject6 = UserProject.builder().id(key6).position("기획자").status("매칭중").build();

        user1.addUserProject(userProject1);
        project1.addUserProject(userProject1);
        userProjectRepository.save(userProject1);

        user1.addUserProject(userProject2);
        project2.addUserProject(userProject2);
        userProjectRepository.save(userProject2);

        user2.addUserProject(userProject3);
        project2.addUserProject(userProject3);
        userProjectRepository.save(userProject3);

        user2.addUserProject(userProject4);
        project3.addUserProject(userProject4);
        userProjectRepository.save(userProject4);

        user3.addUserProject(userProject5);
        project1.addUserProject(userProject5);
        userProjectRepository.save(userProject5);

        user3.addUserProject(userProject6);
        project3.addUserProject(userProject6);
        userProjectRepository.save(userProject6);

        Comment comment1 = Comment.builder().content("테스트 댓글1").createdDate(LocalDateTime.now()).build();

        Comment comment2 = Comment.builder().content("테스트 댓글2").createdDate(LocalDateTime.now()).build();

        Comment comment3 = Comment.builder().content("테스트 댓글3").createdDate(LocalDateTime.now()).build();

        user2.addComment(comment1);
        project1.addComment(comment1);
        commentRepository.save(comment1);

        user3.addComment(comment2);
        project2.addComment(comment2);
        commentRepository.save(comment2);

        user1.addComment(comment3);
        project1.addComment(comment3);
        commentRepository.save(comment3);

    }
}

package com.matching;

import com.matching.domain.*;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.*;

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

        StringBuilder stringBuilder = new StringBuilder();
        IntStream.rangeClosed(1, 600).forEach(stringBuilder::append);

        IntStream.rangeClosed(1, 40).forEach(index -> userRepository.save(User.builder().email("test" + index + "@email.com")
                                            .nick("testUser_" + index).password("testPassword").createdDate(LocalDateTime.now()).build()));

        IntStream.rangeClosed(1, 200).forEach(index -> createProjectTestData(index ,stringBuilder.toString()));

        IntStream.rangeClosed(1, 300).forEach(index -> createUserProjectTestData());

        IntStream.rangeClosed(1, 100).forEach(this::createCommentTestData);
    }

    private void createProjectTestData(int index, String content) {
        Random random = new Random();
        String[] location = {"부산", "서울", "창원", "인천", "대구", "제주도", "춘천", "강릉", "울산", "포항"};
        String[] position = {"개발자", "기획자", "디자이너", "마케터", "기타"};
        String title = "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. ";
        User user = userRepository.findByIdx(random.nextInt(15) + 1);

        Project project = Project.builder().content("내용들 : " + content).title(title + index).endDate(LocalDateTime.now()).
                startDate(LocalDateTime.now()).deadline(LocalDateTime.now()).location(location[random.nextInt(location.length)]).
                createdDate(LocalDateTime.now()).status("모집").leader(user).build();

        projectRepository.save(project);

        UserProjectKey key = new UserProjectKey(user.getIdx(), project.getIdx());
        UserProject userProject = UserProject.builder().id(key).position(position[random.nextInt(position.length)])
                .status("매칭완료").user(user).project(project).build();

        userProjectRepository.save(userProject);
    }

    private void createUserProjectTestData() {
        Random random = new Random();
        String[] position = {"개발자", "기획자", "디자이너", "마케터", "기타"};
        String[] status = {"매칭중", "매칭완료"};

        long userIdx = random.nextInt(15)+1;
        long projectIdx = random.nextInt(100)+1;

        UserProjectKey key = new UserProjectKey(userIdx, projectIdx);

        if(!userProjectRepository.findById(key).isPresent()) {
            User user = userRepository.findByIdx(userIdx);
            Project project = projectRepository.findByIdx(projectIdx);

            UserProject userProject = UserProject.builder().id(key).position(position[random.nextInt(position.length)])
                    .status(status[random.nextInt(status.length)]).user(user).project(project).build();

            userProjectRepository.save(userProject);
        }
    }

    private void createCommentTestData(int index) {
        Random random = new Random();

        User user = userRepository.findByIdx((long) (random.nextInt(15)+1));
        Project project = projectRepository.findByIdx((long) (random.nextInt(100)+1));
        Comment comment = Comment.builder().content("테스트 댓글 " + index).createdDate(LocalDateTime.now())
                .writer(user).project(project).build();

        commentRepository.save(comment);
    }
}

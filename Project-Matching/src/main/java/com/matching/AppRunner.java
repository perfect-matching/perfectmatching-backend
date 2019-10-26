package com.matching;

import com.matching.domain.*;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.domain.key.ProjectTagKey;
import com.matching.domain.key.UserProjectKey;
import com.matching.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.*;

@Component
@EnableConfigurationProperties
public class AppRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UsedSkillRepository usedSkillRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        Random random = new Random();
        final String profileImg = "https://donghun-dev.kro.kr:8083/api/image/USER_DEFAULT_PROFILE_IMG.png";

        List<String> tags = getDataSet("tag");
        List<String> titles = getDataSet("title");
        List<String> userNames = getDataSet("user");

        for (String tag : tags) {
            tagRepository.save(Tag.builder().text(tag).build());
        }
//
//        int tagSize = tags.size();
//
//        IntStream.rangeClosed(1, 40).forEach(index -> {
//
//            String name = userNames.remove(random.nextInt(userNames.size()));
//
//            userRepository.save(User.builder().email(name + "@email.com")
//                    .nick(name).password(passwordEncoder.encode("testpassword")).createdDate(LocalDateTime.now()).profileImg(profileImg)
//                    .description("저는 이러한 사람입니다.").investTime(4).socialUrl("https://github.com/testUser").build());
//        });
//
//        IntStream.rangeClosed(1, 300).forEach(index -> userSkillRepository.save(UserSkill.builder().user(userRepository.findByIdx
//                ((long) random.nextInt(40) + 1)).text(tagRepository.findByIdx((long) (random.nextInt(tagSize) + 1)).getText()).build()));
//
//        IntStream.rangeClosed(1, 200).forEach(index -> createProjectTestData(titles));
//
//        IntStream.rangeClosed(1, 200).forEach(index -> doneProjectRepository.save(DoneProject.builder().user(userRepository.findByIdx((
//                long) random.nextInt(40) + 1)).title("테스트 프로젝트 " + index).summary("테스트 프로젝트 " + index + "입니다.").content("테스트 내용 " + index).
//                createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).projectIdx(projectRepository.findByIdx((
//                long) random.nextInt(200) + 1).getIdx()).build()));
//
//        IntStream.rangeClosed(1, 300).forEach(index -> {
//            usedSkillRepository.save(UsedSkill.builder().doneProject(doneProjectRepository.findByIdx((long) random.nextInt(200) + 1))
//                    .text(tagRepository.findByIdx((long) (random.nextInt(tagSize) + 1)).getText()).build());
//        });
//
//        IntStream.rangeClosed(1, 400).forEach(index -> createProjectTagTestData());
//
//        IntStream.rangeClosed(1, 300).forEach(index -> createUserProjectTestData());
//
//        IntStream.rangeClosed(1, 400).forEach(this::createCommentTestData);

    }

    private void createProjectTestData(List<String> titles) {
        Random random = new Random();
        String title = titles.remove(random.nextInt(titles.size()));
        User user = userRepository.findByIdx(random.nextInt(40) + 1);

        String username = user.getNick();

        Project project = Project.builder().content("안녕하세요 :D<br>" +
                "부산에서 서버 사이드 개발자로 활동하고 있는 " + username + "입니다.<br>"
                + title + "에서 함께 프로젝트를 진행하실 분들을 모집합니다.<br>" +
                "<br>" +
                "현재 저희 프로젝트는 저를 포함한 서버 개발자 2명, 디자이너 2명, 기획자 1명으로 구성되어 있습니다.<br>" +
                "저희는 서버는 Spring Boot, 데이터베이스는 Mysql로 진행 할 예정이며, 프론트 엔드 개발자님을 기다리고 있습니다.<br>" +
                "프론트 엔드의 기술은 현재 정해진 바가 없어서, 원하시는 기술 사용하시면 될거 같습니다.<br>" +
                "저희의 REST API를 이용한 웹서비스를 런칭 하는 것을 목표로 하고 있습니다.<br>" +
                "<br>" +
                "많은 지원과 관심 부탁드립니다.<br>").title(title + "에서 진행하는 사이드 프로젝트 팀원을 구합니다.").
                summary(title + "에서 프론트앤드 프로젝트  ").
                location(LocationType.getRandomLocationType()).createdDate(LocalDateTime.now()).status(ProjectStatus.getRandomProjectStatus()).leader(user).
                developerRecruits(random.nextInt(6)).designerRecruits(random.nextInt(6)).plannerRecruits(random.nextInt(6)).
                marketerRecruits(random.nextInt(6)).etcRecruits(random.nextInt(6)).socialUrl("https://github.com/testUser/testProject").build();

        projectRepository.save(project);

        UserProjectKey key = new UserProjectKey(user.getIdx(), project.getIdx());
        UserProject userProject = UserProject.builder().id(key).position(PositionType.LEADER)
                .status(UserProjectStatus.MATCHING).simpleProfile("저는 꼭 프로젝트에 참여하고 싶습니다").user(user).project(project).build();

        userProjectRepository.save(userProject);

    }


    private void createUserProjectTestData() {
        Random random = new Random();

        long userIdx = random.nextInt(40) + 1;
        long projectIdx = random.nextInt(200) + 1;

        UserProjectKey key = new UserProjectKey(userIdx, projectIdx);

        if (!userProjectRepository.findById(key).isPresent()) {
            User user = userRepository.findByIdx(userIdx);
            Project project = projectRepository.findByIdx(projectIdx);

            UserProject userProject = UserProject.builder().id(key).position(PositionType.getRandomPositionType())
                    .status(UserProjectStatus.getRandomUserProjectStatus()).simpleProfile("저는 꼭 프로젝트에 참여하고 싶습니다").user(user).project(project).build();

            userProjectRepository.save(userProject);
        }
    }

    private void createCommentTestData(int index) {
        Random random = new Random();

        User user = userRepository.findByIdx((long) (random.nextInt(40) + 1));
        Project project = projectRepository.findByIdx((long) (random.nextInt(200) + 1));
        Comment comment = Comment.builder().content("테스트 댓글 " + index).createdDate(LocalDateTime.now())
                .writer(user).project(project).build();

        commentRepository.save(comment);
    }

    private void createProjectTagTestData() {
        Random random = new Random();

        long projectIdx = random.nextInt(200) + 1;
        long tagIdx = random.nextInt(1198) + 1;

        ProjectTagKey key = new ProjectTagKey(projectIdx, tagIdx);

        if (!projectTagRepository.findById(key).isPresent()) {
            Project project = projectRepository.findByIdx(projectIdx);
            Tag tag = tagRepository.findByIdx(tagIdx);

            ProjectTag projectTag = ProjectTag.builder().id(key).project(project).tag(tag).build();
            projectTagRepository.save(projectTag);
        }

    }

    private List<String> getDataSet(String subject) {
        List<String> list = new ArrayList<>();
        try {
            File file = null;

            if (subject.equals("tag"))
                file = new File("tags.txt");
            else if (subject.equals("user"))
                file = new File("name.txt");
            else
                file = new File("title.txt");

            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while ((line = bufReader.readLine()) != null) {
                list.add(line);
            }

            bufReader.close();
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        } catch (IOException e) {
            System.out.println(e);
        }

        return list;
    }

}
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.*;

@Component
@EnableConfigurationProperties
public class AppRunner implements ApplicationRunner {
    private final String TEST_CONTENT = "1. 동해물과 백두산이 마르고 닳도록 \n" +
            "하느님이 보우하사 우리나라 만세\n" +
            "무궁화 삼천리 화려 강산\n" +
            "대한 사람 대한으로 길이 보전하세\n" +
            "\n" +
            "2. 남산 위에 저 소나무 철갑을 두른 듯 \n" +
            "바람 서리 불변함은 우리 기상일세\n" +
            "무궁화 삼천리 화려 강산 \n" +
            "대한 사람 대한으로 길이 보전하세\n" +
            "\n" +
            "3. 가을 하늘 공활한데 높고 구름 없이 \n" +
            "밝은 달은 우리 가슴 일편단심일세\n" +
            "무궁화 삼천리 화려 강산\n" +
            "대한 사람 대한으로 길이 보전하세\n" +
            "\n" +
            "4. 이 기상과 이 맘으로 충성을 다하여 \n" +
            "괴로우나 즐거우나 나라 사랑하세\n" +
            "무궁화 삼천리 화려 강산\n" +
            "대한 사람 대한으로 길이 보전하세";

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
    public void run(ApplicationArguments args) throws IOException {
        Random random = new Random();
        final String profileImg = "https://donghun-dev.kro.kr:8083/api/img/USER_DEFAULT_PROFILE_IMG.png";

        List<String> list = getTagsDataSet();

        for(String tag : list) {
            tagRepository.save(Tag.builder().text(tag).build());
        }

        int tagSize = list.size();

        IntStream.rangeClosed(1, 40).forEach(index -> userRepository.save(User.builder().email("test" + index + "@email.com")
                .nick("testUser_" + index).password(passwordEncoder.encode("testpassword")).createdDate(LocalDateTime.now()).profileImg(profileImg)
                .description("저는 이러한 사람입니다.").investTime(4).socialUrl("https://github.com/testUser").build()));

        IntStream.rangeClosed(1, 300).forEach(index -> userSkillRepository.save(UserSkill.builder().user(userRepository.findByIdx
                ((long)random.nextInt(40)+1)).text(tagRepository.findByIdx((long)(random.nextInt(tagSize) + 1)).getText()).build()));

        IntStream.rangeClosed(1, 200).forEach(this::createProjectTestData);

        IntStream.rangeClosed(1, 200).forEach(index -> doneProjectRepository.save(DoneProject.builder().user(userRepository.findByIdx((
                long)random.nextInt(40)+1)).title("테스트 프로젝트 " + index).summary("테스트 프로젝트 " + index + "입니다.").content("테스트 내용 " + index).
                createdDate(LocalDateTime.now()).startDate(LocalDateTime.now()).endDate(LocalDateTime.now()).projectIdx(projectRepository.findByIdx((
                long)random.nextInt(200)+1).getIdx()).build()));

        IntStream.rangeClosed(1, 300).forEach(index -> {
            usedSkillRepository.save(UsedSkill.builder().doneProject(doneProjectRepository.findByIdx((long) random.nextInt(200) + 1))
                    .text(tagRepository.findByIdx((long)(random.nextInt(tagSize) + 1)).getText()).build());
        });

        IntStream.rangeClosed(1, 400).forEach(index -> createProjectTagTestData());

        IntStream.rangeClosed(1, 300).forEach(index -> createUserProjectTestData());

        IntStream.rangeClosed(1, 400).forEach(this::createCommentTestData);

    }

    private void createProjectTestData(int index) {
        Random random = new Random();
        String title = "이러 이러한 Side Project 의 함께할 사람들을 찾고 있습니다. ";
        User user = userRepository.findByIdx(random.nextInt(40) + 1);

        Project project = Project.builder().content(TEST_CONTENT).title(title + index).
                summary("이러한 프로젝트에 참여할 인원을 모집합니다.").
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

        long userIdx = random.nextInt(40)+1;
        long projectIdx = random.nextInt(200)+1;

        UserProjectKey key = new UserProjectKey(userIdx, projectIdx);

        if(!userProjectRepository.findById(key).isPresent()) {
            User user = userRepository.findByIdx(userIdx);
            Project project = projectRepository.findByIdx(projectIdx);

            UserProject userProject = UserProject.builder().id(key).position(PositionType.getRandomPositionType())
                    .status(UserProjectStatus.getRandomUserProjectStatus()).simpleProfile("저는 꼭 프로젝트에 참여하고 싶습니다").user(user).project(project).build();

            userProjectRepository.save(userProject);
        }
    }

    private void createCommentTestData(int index) {
        Random random = new Random();

        User user = userRepository.findByIdx((long) (random.nextInt(40)+1));
        Project project = projectRepository.findByIdx((long) (random.nextInt(200)+1));
        Comment comment = Comment.builder().content("테스트 댓글 " + index).createdDate(LocalDateTime.now())
                .writer(user).project(project).build();

        commentRepository.save(comment);
    }

    private void createProjectTagTestData() {
        Random random = new Random();

        long projectIdx = random.nextInt(200)+1;
        long tagIdx = random.nextInt(465)+1;

        ProjectTagKey key = new ProjectTagKey(projectIdx, tagIdx);

        if(!projectTagRepository.findById(key).isPresent()) {
            Project project = projectRepository.findByIdx(projectIdx);
            Tag tag = tagRepository.findByIdx(tagIdx);

            ProjectTag projectTag = ProjectTag.builder().id(key).project(project).tag(tag).build();
            projectTagRepository.save(projectTag);
        }

    }

    private List<String> getTagsDataSet() throws IOException {
        List<String> list = new ArrayList<>();
        try {
            File file = new File("tags.txt");
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
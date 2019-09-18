package com.matching.service;

import com.matching.config.auth.JwtResolver;
import com.matching.controller.CommentController;
import com.matching.controller.ProfileController;
import com.matching.controller.ProjectController;
import com.matching.controller.TagController;
import com.matching.domain.*;
import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.*;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.domain.key.ProjectTagKey;
import com.matching.domain.key.UserProjectKey;
import com.matching.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserProjectRepository userProjectRepo;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProjectTagRepository projectTagRepo;

    public boolean projectNullCheck(Long idx) {
        return projectRepository.findByIdx(idx) == null;
    }

    private Page<Project> findPosition(String position, Pageable pageable) {
        if(position.equals("DEVELOPER"))
            return projectRepository.findByDeveloperRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("DESIGNER"))
            return projectRepository.findByDesignerRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("MARKETER"))
            return projectRepository.findByMarketerRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("PLANNER"))
            return projectRepository.findByPlannerRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("ETC"))
            return projectRepository.findByEtcRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("ALL"))
            return projectRepository.findAllByOrderByIdxDesc(pageable);
        return null;
    }

    private Page<Project> findByProjectAllCheck(String position, LocationType location, Pageable pageable) {
        if(position.equals("ALL") && location.getLocation().equals("ALL"))
            return projectRepository.findAllByOrderByIdxDesc(pageable);
        else if(position.equals("ALL") && !location.getLocation().equals("ALL"))
            return projectRepository.findByLocationOrderByIdxDesc(location, pageable);
        else if(!position.equals("ALL") && location.getLocation().equals("ALL"))
            return  findPosition(position, pageable);
        else
            return findPositionAndLocation(position, location, pageable);
    }

    private Page<Project> findPositionAndLocation(String position, LocationType location, Pageable pageable) {
        if(position.equals("DEVELOPER"))
            return projectRepository.findByLocationAndDeveloperRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("DESIGNER"))
            return projectRepository.findByLocationAndDesignerRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("MARKETER"))
            return projectRepository.findByLocationAndMarketerRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("PLANNER"))
            return projectRepository.findByLocationAndPlannerRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("ETC"))
            return projectRepository.findByLocationAndEtcRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        return null;
    }


    public Page<Project> findAllProject(Pageable pageable, LocationType location, String position) {
        if(position == null && location == null)
            return projectRepository.findAllByOrderByIdxDesc(pageable);
        else if(position == null && location != null) {
            if (location.getLocation().equals("ALL"))
                return projectRepository.findAllByOrderByIdxDesc(pageable);
            return projectRepository.findByLocationOrderByIdxDesc(location, pageable);
        }
        else if(position != null && location == null)
            return findPosition(position, pageable);
        else
            return findByProjectAllCheck(position, location, pageable);
    }

    public Project findByProject(Long idx) {
        return projectRepository.findByIdx(idx);
    }

    public List<Resource> getProjectsDTOList(Page<Project> page) {

        List<Resource> list = new ArrayList<>();

        for(Project project : page) {
            ProjectsDTO projectsDTO = new ProjectsDTO(project);
            Resource<?> resource = new Resource<>(projectsDTO);
            resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(project.getIdx())).withSelfRel());
            resource.add(linkTo(methodOn(ProfileController.class).getProfile(project.getLeader().getIdx())).withRel("Leader Profile"));
            list.add(resource);
        }

        return list;
    }

    public Resource<?> getProject(Long idx) {
        Project project = projectRepository.findByIdx(idx);
        ProjectDTO projectDTO = new ProjectDTO(project, userProjectRepo);
        return new Resource<>(projectDTO);
    }

    public String getCurrentUriGetString() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    }

    public String nextUriEncoding(String uriString, Page<Project> collection, LocationType location, String position) {
        int pageNumber = collection.getNumber();
        if(uriString.contains("?offset=" + pageNumber))
            uriString = uriString.replace("?offset=" + pageNumber, "?offset="+ (pageNumber + 1));
        else if(uriString.contains("&offset=" + pageNumber))
            uriString = uriString.replace("&offset=" + pageNumber, "&offset="+ (pageNumber + 1));
        else if(!uriString.contains("&offset=" + pageNumber) && (location != null || position != null) )
            uriString += "&offset="+ (pageNumber + 1);
        else
            uriString += "?offset="+ (pageNumber + 1);

        return uriString;
    }

    public boolean findProjectComments(Long idx) {
        if(projectRepository.findByIdx(idx) == null)
            return true;
        return commentRepository.findByProject(projectRepository.findByIdx(idx)) == null;
    }

    public Resources<?> getProjectComments(Long idx) {
        List<Resource> list = new ArrayList<>();
        for(Comment comment : commentRepository.findByProject(projectRepository.findByIdx(idx))) {
            CommentDTO commentDTO = new CommentDTO(comment);
            Resource<?> resource = new Resource<>(commentDTO);
            resource.add(linkTo(methodOn(CommentController.class).getComment(comment.getIdx())).withSelfRel());
            list.add(resource);
        }
        return new Resources<>(list);
    }

    public boolean findProjectMembers(Long idx) {
        if(projectRepository.findByIdx(idx) == null)
            return true;
        return userProjectRepo.findByProjectAndStatus(projectRepository.findByIdx(idx), UserProjectStatus.MATCHING) == null;
    }

    public Resources<?> getProjectMembers(Long idx) {
        List<Resource> list = new ArrayList<>();
        List<UserProject> userProjectList = userProjectRepo.findByProjectAndStatus(projectRepository.findByIdx(idx), UserProjectStatus.MATCHING);

        for(UserProject userProject : userProjectList) {
            MemberDTO memberDTO = new MemberDTO(userProject);
            Resource<?> resource = new Resource<>(memberDTO);
            resource.add(linkTo(methodOn(ProfileController.class).getProfile(memberDTO.getMemberIdx())).withRel("Profile"));
            list.add(resource);
        }

        return new Resources<>(list);
    }

    public boolean findProjectTags(Long idx) {
        if(projectRepository.findByIdx(idx) == null)
            return true;
        return projectTagRepo.findByProject(projectRepository.findByIdx(idx)) == null;
    }

    public Resources<?> getProjectTags(Long idx) {
        List<Resource> list = new ArrayList<>();
        List<ProjectTag> projectTagList = projectTagRepo.findByProject(projectRepository.findByIdx(idx));

        for(ProjectTag projectTag : projectTagList) {
            Tag tag = projectTag.getTag();
            Resource<?> resource = new Resource<>(tag);
            resource.add(linkTo(methodOn(TagController.class).getTag(tag.getIdx())).withSelfRel());
            list.add(resource);
        }

        return new Resources<>(list);
    }

    public StringBuilder validation(BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    public ResponseEntity<?> postProject(ProjectDTO projectDTO, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());

        Project project = Project.builder().title(projectDTO.getTitle()).location(getLocation(projectDTO.getLocation())).summary(projectDTO.getSummary()).content(projectDTO.getContent())
                .socialUrl(projectDTO.getSocialUrl()).designerRecruits(projectDTO.getDesignerRecruits()).developerRecruits(projectDTO.getDeveloperRecruits()).plannerRecruits(projectDTO.getPlannerRecruits())
                .marketerRecruits(projectDTO.getMarketerRecruits()).etcRecruits(projectDTO.getEtcRecruits()).createdDate(LocalDateTime.now()).status(ProjectStatus.RECRUIT).build();

        user.addProject(project);
        projectRepository.save(project);

        UserProject userProject = UserProject.builder().id(new UserProjectKey(user.getIdx(), project.getIdx())).position(PositionType.LEADER).status(UserProjectStatus.MATCHING).build();

        user.addUserProject(userProject);
        project.addUserProject(userProject);
        userProjectRepo.save(userProject);

        for(Tag tag : projectDTO.getTags()) {
            Tag foundTag = tagRepository.findByText(tag.getText()) == null ? tagRepository.save(tag) : tagRepository.findByText(tag.getText());

            ProjectTag projectTag = ProjectTag.builder().id(new ProjectTagKey(project.getIdx(), foundTag.getIdx())).build();

            project.addProjectTag(projectTag);
            foundTag.addProjectTag(projectTag);
            projectTagRepo.save(projectTag);
        }
        return new ResponseEntity<>(project, restDocs.getHttpHeaders(), HttpStatus.CREATED);
    }

    private LocationType getLocation(String location) {
        LocationType locationType = null;

        for(int i=0; i<LocationType.values().length; i++) {
            locationType = LocationType.getLocation(i);
            if(locationType.getLocation().equals(location))
                return locationType;
        }

        return locationType;
    }

    public ResponseEntity<?> putProject(ProjectDTO projectDTO, HttpServletRequest request, Long idx, RestDocs restDocs) {

        Project project = projectRepository.findByIdx(idx);
        JwtResolver jwtResolver = new JwtResolver(request);

        if(!userRepository.findByEmail(jwtResolver.getUserByToken()).getIdx().equals(project.getLeader().getIdx()))
            return new ResponseEntity<>("프로젝트 개설자가 아닙니다.", HttpStatus.BAD_REQUEST);

        project.setTitle(projectDTO.getTitle());
        project.setContent(projectDTO.getContent());
        project.setSummary(projectDTO.getSummary());
        project.setLocation(getLocation(projectDTO.getLocation()));
        project.setModifiedDate(LocalDateTime.now());
        project.setDeveloperRecruits(projectDTO.getDeveloperRecruits());
        project.setDesignerRecruits(projectDTO.getDesignerRecruits());
        project.setPlannerRecruits(projectDTO.getPlannerRecruits());
        project.setMarketerRecruits(projectDTO.getMarketerRecruits());
        project.setEtcRecruits(projectDTO.getEtcRecruits());
        project.setSocialUrl(projectDTO.getSocialUrl());

        project.getProjectTags().clear();
        projectRepository.save(project);

        projectTagRepo.deleteByProject(project);

        for(Tag tag : projectDTO.getTags()) {
            Tag foundTag = tagRepository.findByText(tag.getText()) == null ? tagRepository.save(tag) : tagRepository.findByText(tag.getText());

            ProjectTag projectTag = ProjectTag.builder().id(new ProjectTagKey(project.getIdx(), foundTag.getIdx())).build();

            project.addProjectTag(projectTag);
            foundTag.addProjectTag(projectTag);
            projectTagRepo.save(projectTag);
        }

        return new ResponseEntity<>(project, restDocs.getHttpHeaders(), HttpStatus.OK);

    }

    public ResponseEntity<?> deleteProject(Long idx, HttpServletRequest request, RestDocs restDocs) {
        Project project = projectRepository.findByIdx(idx);
        JwtResolver jwtResolver = new JwtResolver(request);

        if(!userRepository.findByEmail(jwtResolver.getUserByToken()).equals(project.getLeader()))
            return new ResponseEntity<>("본인이 개설한 프로젝트만 지울 수 있습니다.", HttpStatus.BAD_REQUEST);
        else if(project.getStatus().getStatus().equals("진행중"))
            return new ResponseEntity<>("진행중인 프로젝트는 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);

        projectRepository.deleteById(project.getIdx());

        return new ResponseEntity<>(project, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<?> putProjectStatus(Long idx, String status, HttpServletRequest request, RestDocs restDocs) {

        if(status == null || (!StringUtils.equals(status, "PROGRESS") && !StringUtils.equals(status, "COMPLETE")))
            return new ResponseEntity<>("status 값이 올바르지 않습니다.1", HttpStatus.BAD_REQUEST);
        
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        Project project = projectRepository.findByIdx(idx);

        if(!user.getIdx().equals(project.getLeader().getIdx()))
            return new ResponseEntity<>("프로젝트 개설자가 아닙니다.", HttpStatus.BAD_REQUEST);

        if(status.equals("PROGRESS"))
            project.setStatus(ProjectStatus.PROGRESS);
        else
            project.setStatus(ProjectStatus.COMPLETE);

        projectRepository.save(project);
        return new ResponseEntity<>(project, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<?> postProjectApply(ProjectApplyDTO projectApplyDTO, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        Project project = projectRepository.findByIdx(projectApplyDTO.getProjectIdx());

        UserProject userProject = UserProject.builder().id(new UserProjectKey(user.getIdx(), project.getIdx())).position(getPosition(projectApplyDTO.getPosition()))
                .simpleProfile(projectApplyDTO.getSimpleProfile()).status(UserProjectStatus.WAIT).build();

        user.addUserProject(userProject);
        project.addUserProject(userProject);
        userProjectRepo.save(userProject);

        return new ResponseEntity<>(userProject, restDocs.getHttpHeaders(), HttpStatus.CREATED);
    }

    private PositionType getPosition(String position) {
        PositionType positionType = null;

        for(int i=0; i<PositionType.values().length; i++) {
            positionType = PositionType.getPosition(i);
            if(positionType.getPosition().equals(position))
                return positionType;
        }

        return positionType;
    }

    public ResponseEntity<?> putProjectMatching(Map<String, String> map, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        Project project = projectRepository.findByIdx(Long.parseLong(map.get("projectIdx")));

        if(!user.getIdx().equals(project.getLeader().getIdx()))
            return new ResponseEntity<>("프로젝트 개설자가 아닙니다.", HttpStatus.BAD_REQUEST);

        UserProject userProject = userProjectRepo.findByUserAndProject(userRepository.findByIdx(Long.parseLong(map.get("userIdx"))), project);

        userProject.setStatus(map.get("status").equals("매칭") ? UserProjectStatus.MATCHING : UserProjectStatus.FAIL);
        userProjectRepo.save(userProject);

        return new ResponseEntity<>(userProject, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteProjectCancel(Long idx, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        Project project = projectRepository.findByIdx(idx);

        userProjectRepo.deleteById(new UserProjectKey(user.getIdx(), project.getIdx()));

        return new ResponseEntity<>("{}", restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    public boolean findProjectJoinMembers(Long idx) {
        if(projectRepository.findByIdx(idx) == null)
            return true;
        return userProjectRepo.findByProjectAndStatus(projectRepository.findByIdx(idx), UserProjectStatus.WAIT) == null;
    }

    public Resources<?> getProjectJoinMembers(Long idx) {
        List<Resource> list = new ArrayList<>();
        List<UserProject> userProjectList = userProjectRepo.findByProjectAndStatus(projectRepository.findByIdx(idx), UserProjectStatus.WAIT);

        for(UserProject userProject : userProjectList) {
            MemberDTO memberDTO = new MemberDTO(userProject);
            Resource<?> resource = new Resource<>(memberDTO);
            resource.add(linkTo(methodOn(ProfileController.class).getProfile(memberDTO.getMemberIdx())).withRel("Profile"));
            list.add(resource);
        }

        return new Resources<>(list);
    }
}
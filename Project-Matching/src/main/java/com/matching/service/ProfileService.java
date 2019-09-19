package com.matching.service;

import com.matching.config.auth.JwtResolver;
import com.matching.controller.DoneProjectController;
import com.matching.controller.TagController;
import com.matching.domain.DoneProject;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.UserSkill;
import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.DoneProjectDTO;
import com.matching.domain.dto.ProfileDTO;
import com.matching.domain.dto.ProcessingProjectDTO;
import com.matching.domain.dto.UserDTO;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.repository.DoneProjectRepository;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import com.matching.repository.UserSkillRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    public boolean findUser(Long idx) {
        return userRepository.findByIdx(idx) == null;
    }

    public Resource<?> findUserProfile(Long idx) {

        User user = userRepository.findByIdx(idx);
        ProfileDTO profileDTO = new ProfileDTO(user);

        return new Resource<>(profileDTO);
    }

    public boolean findProfileSkills(Long idx) {
        if(userRepository.findByIdx(idx) == null)
            return true;
        return userSkillRepository.findByUser(userRepository.findByIdx(idx)) == null;
    }

    public Resources<?> getProfileSkills(Long idx) {
        User user = userRepository.findByIdx(idx);
        List<Resource> list = new ArrayList<>();
        List<UserSkill> userSkillList = userSkillRepository.findByUser(user);

        for(UserSkill userSkill : userSkillList) {
            Resource<?> resource = new Resource<>(userSkill);
            resource.add(linkTo(methodOn(TagController.class).getUserSkill(userSkill.getIdx())).withSelfRel());
            list.add(resource);
        }

        return new Resources<>(list);
    }

    public boolean findProfileProjects(Long idx) {
        if(userRepository.findByIdx(idx) == null)
            return  true;
        return userProjectRepository.findByUserAndProject_StatusAndStatusAndPositionNotOrderByProjectDesc(userRepository.findByIdx(idx),
                ProjectStatus.PROGRESS, UserProjectStatus.MATCHING, PositionType.LEADER) == null;
    }

    public Resources<?> getProfileProjects(Long idx) {
        User user = userRepository.findByIdx(idx);
        List<ProcessingProjectDTO> list = new ArrayList<>();
        List<UserProject> userProjectList = userProjectRepository.findByUserAndProject_StatusAndStatusAndPositionNotOrderByProjectDesc(user,
                ProjectStatus.PROGRESS, UserProjectStatus.MATCHING, PositionType.LEADER);

        for(UserProject userProject : userProjectList) {
            list.add(new ProcessingProjectDTO(userProject));
        }

        return new Resources<>(list);
    }

    public boolean findProfileDoneProjects(Long idx) {
        if(userRepository.findByIdx(idx) == null)
            return true;
        return doneProjectRepository.findByUser(userRepository.findByIdx(idx)) == null;
    }

    public Resources<?> getProfileDoneProjects(Long idx) {
        User user = userRepository.findByIdx(idx);
        List<Resource> resourceList = new ArrayList<>();
        List<DoneProject> doneProjectList = doneProjectRepository.findByUser(user);

        for(DoneProject doneProject : doneProjectList) {
            Resource<?> resource = new Resource<>(new DoneProjectDTO(doneProject));
            resource.add(linkTo(methodOn(DoneProjectController.class).getDoneProject(doneProject.getIdx())).withSelfRel());
            resourceList.add(resource);
        }

        return new Resources<>(resourceList);
    }

    public boolean findByProfileMyProjects(Long idx) {
        if(userRepository.findByIdx(idx) == null)
            return  true;
        return userProjectRepository.findByUserAndStatusAndPositionOrderByProjectDesc(userRepository.findByIdx(idx),
                UserProjectStatus.MATCHING, PositionType.LEADER) == null;
    }

    public Resources<?> getProfileMyProjects(Long idx) {
        User user = userRepository.findByIdx(idx);
        List<ProcessingProjectDTO> list = new ArrayList<>();
        List<UserProject> userProjectList = userProjectRepository.findByUserAndStatusAndPositionOrderByProjectDesc(user,
                UserProjectStatus.MATCHING, PositionType.LEADER);

        for(UserProject userProject : userProjectList) {
            list.add(new ProcessingProjectDTO(userProject));
        }

        return new Resources<>(list);
    }

    public boolean findUserNick(String nick, HttpServletRequest request) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());

        String currentNick = user.getNick();

        if(userRepository.findByNick(nick) != null && currentNick.equals(nick))
            return false;
        else if(userRepository.findByNick(nick) != null && !currentNick.equals(nick))
            return true;
        else
            return false;
    }

    public StringBuilder validation(BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    public ResponseEntity<?> putProfile(@Valid ProfileDTO profileDTO, HttpServletRequest request, Long idx, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());

        if(!user.getIdx().equals(idx)) {
            return new ResponseEntity<>("{\"message\": \"잘못된 요청입니다.\"}", restDocs.getHttpHeaders(),HttpStatus.BAD_REQUEST);
        }

        user.setNick(profileDTO.getNickname());
        user.setDescription(profileDTO.getSummary());
        user.setSocialUrl(profileDTO.getSocialUrl());
        user.setInvestTime(profileDTO.getInvestTime());

        userRepository.save(user);

        for(UserSkill userSkill : profileDTO.getUserSkills()) {
            user.addUserSkill(userSkill);
            userSkillRepository.save(userSkill);
        }

        return new ResponseEntity<>("{}", restDocs.getHttpHeaders(),HttpStatus.OK);

    }

    public boolean findProfileApplyProjects(Long idx) {
        if(userRepository.findByIdx(idx) == null)
            return  true;
        return userProjectRepository.findByUserAndStatusAndPositionNotOrderByProjectDesc(userRepository.findByIdx(idx),
                UserProjectStatus.WAIT, PositionType.LEADER) == null;
    }

    public Resources<?> getProfileApplyProjects(Long idx) {
        User user = userRepository.findByIdx(idx);
        List<ProcessingProjectDTO> list = new ArrayList<>();
        List<UserProject> userProjectList = userProjectRepository.findByUserAndStatusAndPositionNotOrderByProjectDesc(user, UserProjectStatus.WAIT,
                PositionType.LEADER);

        for(UserProject userProject : userProjectList) {
            list.add(new ProcessingProjectDTO(userProject));
        }

        return new Resources<>(list);
    }

}

package com.matching.service;

import com.matching.config.auth.JwtResolver;
import com.matching.controller.TagController;
import com.matching.domain.DoneProject;
import com.matching.domain.UsedSkill;
import com.matching.domain.User;
import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.DoneProjectDTO;
import com.matching.repository.DoneProjectRepository;
import com.matching.repository.UsedSkillRepository;
import com.matching.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class DoneProjectService {

    @Autowired
    private DoneProjectRepository doneProjectRepository;

    @Autowired
    private UsedSkillRepository usedSkillRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean findByDoneProject(Long idx) {
        return doneProjectRepository.findByIdx(idx) == null;
    }

    public Resource<?> getDoneProject(Long idx) {
        return new Resource<>(new DoneProjectDTO(doneProjectRepository.findByIdx(idx)));
    }

    public boolean findByDoneProjectUsedSkills(Long idx) {
        if(doneProjectRepository.findByIdx(idx) == null)
            return true;
        return usedSkillRepository.findByDoneProject(doneProjectRepository.findByIdx(idx)) == null;
    }

    public Resources<?> getDoneProjectUsedSkills(Long idx) {
        DoneProject doneProject = doneProjectRepository.findByIdx(idx);
        List<Resource> resourceList = new ArrayList<>();
        List<UsedSkill> usedSkills = usedSkillRepository.findByDoneProject(doneProject);

        for(UsedSkill usedSkill : usedSkills) {
            Resource<?> resource = new Resource<>(usedSkill);
            resource.add(linkTo(methodOn(TagController.class).getUsedSkill(usedSkill.getIdx())).withSelfRel());
            resourceList.add(resource);
        }

        return new Resources<>(resourceList);
    }

    public StringBuilder validation(BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    public ResponseEntity<?> postDoneProject(DoneProjectDTO doneProjectDTO, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());

        DoneProject doneProject = DoneProject.builder().title(doneProjectDTO.getTitle()).summary(doneProjectDTO.getSummary()).
                content(doneProjectDTO.getContent()).socialUrl(doneProjectDTO.getSocialUrl()).projectIdx(doneProjectDTO.getProjectIdx()).
                createdDate(LocalDateTime.now()).endDate(doneProjectDTO.getEndDate()).startDate(doneProjectDTO.getStartDate()).build();

        user.addDoneProject(doneProject);
        doneProjectRepository.save(doneProject);

        for(UsedSkill usedSkill : doneProjectDTO.getUsedSkills()) {
            doneProject.addUsedSkill(usedSkill);
            usedSkillRepository.save(usedSkill);
        }

        return new ResponseEntity<>(doneProject,  restDocs.getHttpHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> putProject(DoneProjectDTO doneProjectDTO, Long idx, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        DoneProject doneProject = doneProjectRepository.findByIdx(idx);

        if(!user.getIdx().equals(doneProject.getUser().getIdx()))
            return new ResponseEntity<>("프로젝트 개설자가 아닙니다.", restDocs.getHttpHeaders(), HttpStatus.BAD_REQUEST);

        doneProject.setTitle(doneProjectDTO.getTitle());
        doneProject.setSummary(doneProjectDTO.getSummary());
        doneProject.setContent(doneProjectDTO.getContent());
        doneProject.setSocialUrl(doneProjectDTO.getSocialUrl());
        doneProject.setStartDate(doneProjectDTO.getStartDate());
        doneProject.setEndDate(doneProjectDTO.getEndDate());
        doneProject.setModifiedDate(LocalDateTime.now());

        doneProject.getUsedSkills().clear();
        doneProjectRepository.save(doneProject);

        for(UsedSkill usedSkill : doneProjectDTO.getUsedSkills()) {
            doneProject.addUsedSkill(usedSkill);
            usedSkillRepository.save(usedSkill);
        }

        return new ResponseEntity<>(doneProject, restDocs.getHttpHeaders(), HttpStatus.OK);
    }


    public ResponseEntity<?> deleteDoneProject(Long idx, HttpServletRequest request, RestDocs restDocs) {
        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        DoneProject doneProject = doneProjectRepository.findByIdx(idx);

        if(!user.getIdx().equals(doneProject.getUser().getIdx()))
            return new ResponseEntity<>("프로젝트 개설자가 아닙니다.", restDocs.getHttpHeaders(), HttpStatus.BAD_REQUEST);

        doneProjectRepository.deleteById(doneProject.getIdx());

        return new ResponseEntity<>("{}", restDocs.getHttpHeaders(), HttpStatus.OK);
    }
}

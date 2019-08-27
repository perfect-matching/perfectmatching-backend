package com.matching.service;

import com.matching.controller.TagController;
import com.matching.domain.DoneProject;
import com.matching.domain.UsedSkill;
import com.matching.domain.dto.DoneProjectDTO;
import com.matching.repository.DoneProjectRepository;
import com.matching.repository.UsedSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    public Resources<?> getDoneProjectUsedSkills(Long idx, HttpServletResponse response) {
        DoneProject doneProject = doneProjectRepository.findByIdx(idx);
        List<Resource> resourceList = new ArrayList<>();
        List<UsedSkill> usedSkills = usedSkillRepository.findByDoneProject(doneProject);

        for(UsedSkill usedSkill : usedSkills) {
            Resource<?> resource = new Resource<>(usedSkill);
            resource.add(linkTo(methodOn(TagController.class).getUsedSkill(usedSkill.getIdx(), response)).withSelfRel());
            resourceList.add(resource);
        }

        return new Resources<>(resourceList);
    }
}

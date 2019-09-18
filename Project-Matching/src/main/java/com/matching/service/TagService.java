package com.matching.service;

import com.matching.controller.TagController;
import com.matching.domain.Tag;
import com.matching.domain.UsedSkill;
import com.matching.domain.UserSkill;
import com.matching.repository.TagRepository;
import com.matching.repository.UsedSkillRepository;
import com.matching.repository.UserSkillRepository;
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
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UsedSkillRepository usedSkillRepository;

    public boolean findByTags() {
        return tagRepository.findAll() == null;
    }

    public Resources<?> getTags() {
        List<Resource> resources = new ArrayList<>();
        List<Tag> tags = tagRepository.findAll();

        for(Tag tag : tags) {
            Resource<?> resource = new Resource<>(tag);
            resource.add(linkTo(methodOn(TagController.class).getTag(tag.getIdx())).withSelfRel());
            resources.add(resource);
        }

        return new Resources<>(resources);
    }

    public boolean findByTag(Long idx) {
        return tagRepository.findByIdx(idx) == null;
    }

    public Resource<?> getTag(Long idx) {
        return new Resource<>(tagRepository.findByIdx(idx));
    }

    public boolean findByUserSkills() {
        return userSkillRepository.findAll() == null;
    }

    public Resources<?> getUserSkills() {
        List<Resource> resources = new ArrayList<>();
        List<UserSkill> userSkills = userSkillRepository.findAll();

        for(UserSkill userSkill : userSkills) {
            Resource<?> resource = new Resource<>(userSkill);
            resource.add(linkTo(methodOn(TagController.class).getUserSkill(userSkill.getIdx())).withSelfRel());
            resources.add(resource);
        }

        return new Resources<>(resources);
    }

    public boolean findByUserSkill(Long idx) {
        return userSkillRepository.findByIdx(idx) == null;
    }

    public Resource<?> getUserSkill(Long idx) {
        return new Resource<>(userSkillRepository.findByIdx(idx));
    }

    public boolean findByUsedSkills() {
        return usedSkillRepository.findAll() == null;
    }

    public Resources<?> getUsedSkills() {
        List<Resource> resourceList = new ArrayList<>();
        List<UsedSkill> usedSkills = usedSkillRepository.findAll();

        for(UsedSkill usedSkill : usedSkills) {
            Resource<?> resource = new Resource<>(usedSkill);
            resource.add(linkTo(methodOn(TagController.class).getUsedSkill(usedSkill.getIdx())).withSelfRel());
            resourceList.add(resource);
        }

        return new Resources<>(resourceList);
    }

    public boolean findByUsedSkill(Long idx) {
        return usedSkillRepository.findByIdx(idx) == null;
    }

    public Resource<?> getUsedSkill(Long idx) {
        return new Resource<>(usedSkillRepository.findByIdx(idx));
    }

}

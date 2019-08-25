package com.matching.service;

import com.matching.repository.TagRepository;
import com.matching.repository.UsedSkillRepository;
import com.matching.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UsedSkillRepository usedSkillRepository;

    public boolean findByTag(Long idx) {
        return tagRepository.findByIdx(idx) == null;
    }

    public Resource<?> getTag(Long idx) {
        return new Resource<>(tagRepository.findByIdx(idx));
    }

    public boolean findByUserSkill(Long idx) {
        return userSkillRepository.findByIdx(idx) == null;
    }

    public Resource<?> getUserSkill(Long idx) {
        return new Resource<>(userSkillRepository.findByIdx(idx));
    }

    public boolean findByUsedSkill(Long idx) {
        return usedSkillRepository.findByIdx(idx) == null;
    }

    public Resource<?> getUsedSkill(Long idx) {
        return new Resource<>(usedSkillRepository.findByIdx(idx));
    }
}

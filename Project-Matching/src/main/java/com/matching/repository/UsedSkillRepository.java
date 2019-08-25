package com.matching.repository;

import com.matching.domain.DoneProject;
import com.matching.domain.UsedSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedSkillRepository extends JpaRepository<UsedSkill, Long> {
    List<UsedSkill> findByDoneProject(DoneProject doneProject);

    UsedSkill findByIdx(Long idx);
}

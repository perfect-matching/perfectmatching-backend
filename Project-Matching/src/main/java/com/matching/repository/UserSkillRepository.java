package com.matching.repository;

import com.matching.domain.User;
import com.matching.domain.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUser(User user);

    UserSkill findByIdx(Long idx);
}

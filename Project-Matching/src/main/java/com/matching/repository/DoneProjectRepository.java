package com.matching.repository;

import com.matching.domain.DoneProject;
import com.matching.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoneProjectRepository extends JpaRepository<DoneProject, Long> {

    DoneProject findByIdx(Long idx);

    List<DoneProject> findByUser(User user);

    DoneProject findByTitle(String title);
}

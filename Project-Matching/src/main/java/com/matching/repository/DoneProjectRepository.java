package com.matching.repository;

import com.matching.domain.DoneProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneProjectRepository extends JpaRepository<DoneProject, Long> {

    DoneProject findByIdx(Long idx);
}

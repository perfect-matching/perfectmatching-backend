package com.matching.repository;

import com.matching.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByIdx(long idx);

    Page<Project> findAllByOrderByIdxDesc(Pageable pageable);

    Page<Project> findByLocationOrderByIdxDesc(String location, Pageable pageable);
}

package com.matching.repository;

import com.matching.domain.ProjectTag;
import com.matching.domain.key.ProjectTagKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, ProjectTagKey> {
}

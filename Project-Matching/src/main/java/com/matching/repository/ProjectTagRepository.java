package com.matching.repository;

import com.matching.domain.Project;
import com.matching.domain.ProjectTag;
import com.matching.domain.key.ProjectTagKey;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, ProjectTagKey> {
    List<ProjectTag> findByProject(Project project);

    @Transactional
    void deleteByProject(Project project);
}

package com.matching.repository;

import com.matching.domain.Project;
import com.matching.domain.ProjectTag;
import com.matching.domain.Tag;
import com.matching.domain.enums.LocationType;
import com.matching.domain.key.ProjectTagKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, ProjectTagKey> {
    List<ProjectTag> findByProject(Project project);

    @Transactional
    void deleteByProject(Project project);

    Page<ProjectTag> findByProject_LocationAndTagOrderByProjectDesc(LocationType locationType, Tag tag, Pageable pageable);

    Page<ProjectTag> findByTagOrderByProjectDesc(Tag tag, Pageable pageable);
}

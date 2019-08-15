package com.matching.repository;

import com.matching.domain.Project;
import com.matching.domain.enums.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByIdx(long idx);

    Page<Project> findAllByOrderByIdxDesc(Pageable pageable);

    Page<Project> findByLocationOrderByIdxDesc(LocationType location, Pageable pageable);

    Page<Project> findByDeveloperRecruitsIsGreaterThanOrderByIdxDesc(int age, Pageable pageable);
    Page<Project> findByDesignerRecruitsIsGreaterThanOrderByIdxDesc(int age, Pageable pageable);
    Page<Project> findByMarketerRecruitsIsGreaterThanOrderByIdxDesc(int age, Pageable pageable);
    Page<Project> findByPlannerRecruitsIsGreaterThanOrderByIdxDesc(int age, Pageable pageable);
    Page<Project> findByEtcRecruitsIsGreaterThanOrderByIdxDesc(int age, Pageable pageable);

    Page<Project> findByLocationAndDeveloperRecruitsIsGreaterThanOrderByIdxDesc(LocationType location, int age, Pageable pageable);
    Page<Project> findByLocationAndDesignerRecruitsIsGreaterThanOrderByIdxDesc(LocationType locationType, int age, Pageable pageable);
    Page<Project> findByLocationAndMarketerRecruitsIsGreaterThanOrderByIdxDesc(LocationType locationType, int age, Pageable pageable);
    Page<Project> findByLocationAndPlannerRecruitsIsGreaterThanOrderByIdxDesc(LocationType locationType, int age, Pageable pageable);
    Page<Project> findByLocationAndEtcRecruitsIsGreaterThanOrderByIdxDesc(LocationType locationType, int age, Pageable pageable);
}

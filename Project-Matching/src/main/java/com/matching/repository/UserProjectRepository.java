package com.matching.repository;

import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.key.UserProjectKey;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.UserProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {

    Integer countByProjectAndPositionAndStatus(Project project, PositionType positionType, UserProjectStatus status);

    List<UserProject> findByUserOrderByProjectDesc(User user);

    List<UserProject> findByProjectAndStatus(Project project, UserProjectStatus status);
}

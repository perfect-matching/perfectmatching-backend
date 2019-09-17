package com.matching.repository;

import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.key.UserProjectKey;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.UserProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {

    UserProject findByUserAndProject(User user, Project project);

    Integer countByProjectAndPositionAndStatus(Project project, PositionType positionType, UserProjectStatus status);

    List<UserProject> findByUserAndStatusAndPositionOrderByProjectDesc(User user, UserProjectStatus status, PositionType positionType);

    List<UserProject> findByUserAndProject_StatusAndStatusAndPositionNotOrderByProjectDesc(User user, ProjectStatus status, UserProjectStatus userProjectStatus, PositionType positionType);

    List<UserProject> findByProjectAndStatus(Project project, UserProjectStatus status);
}

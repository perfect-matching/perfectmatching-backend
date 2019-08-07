package com.matching.repository;

import com.matching.domain.UserProject;
import com.matching.domain.UserProjectKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectKey> {
}

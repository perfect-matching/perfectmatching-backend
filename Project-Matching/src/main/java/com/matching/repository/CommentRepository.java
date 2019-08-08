package com.matching.repository;

import com.matching.domain.Comment;
import com.matching.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByProject(Project project);

    Comment findByIdx(Long idx);
}

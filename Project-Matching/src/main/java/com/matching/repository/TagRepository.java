package com.matching.repository;

import com.matching.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByIdx(Long tagIdx);
}

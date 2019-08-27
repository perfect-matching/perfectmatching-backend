package com.matching.repository;

import com.matching.domain.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    JwtToken findByToken(String token);
}

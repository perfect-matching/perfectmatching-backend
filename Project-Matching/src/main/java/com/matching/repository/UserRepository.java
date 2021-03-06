package com.matching.repository;

import com.matching.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByIdx(long idx);
    User findByEmail(String email);
    User findByNick(String nick);
}

package com.matching.service;

import com.matching.domain.User;
import com.matching.domain.dto.ProfileDTO;
import com.matching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;


    public boolean findUser(Long idx) {
        return userRepository.findByIdx(idx) == null;
    }

    public Resource<?> findUserProfile(Long idx) {

        User user = userRepository.findByIdx(idx);
        ProfileDTO profileDTO = new ProfileDTO(user);

        return new Resource<>(profileDTO);
    }
}

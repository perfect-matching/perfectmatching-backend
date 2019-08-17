package com.matching.service;

import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.dto.ProfileDTO;
import com.matching.domain.dto.ProfileProjectDTO;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    public boolean findUser(Long idx) {
        return userRepository.findByIdx(idx) == null;
    }

    public boolean findUserProjects(Long idx) {
        User user = userRepository.findByIdx(idx);
        return userProjectRepository.findByUserOrderByProjectDesc(user) == null;
    }

    public Resource<?> findUserProfile(Long idx) {

        User user = userRepository.findByIdx(idx);
        ProfileDTO profileDTO = new ProfileDTO(user);

        return new Resource<>(profileDTO);
    }

    public Resources<?> findProfileProjects(Long idx) {
        User user = userRepository.findByIdx(idx);
        List<ProfileProjectDTO> list = new ArrayList<>();
        List<UserProject> userProjectList = userProjectRepository.findByUserOrderByProjectDesc(user);

        for(UserProject userProject : userProjectList) {
            list.add(new ProfileProjectDTO(userProject));
        }

        return new Resources<>(list);
    }
}

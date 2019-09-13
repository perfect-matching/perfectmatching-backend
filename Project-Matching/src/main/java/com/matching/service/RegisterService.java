package com.matching.service;

import com.matching.domain.User;
import com.matching.domain.UserSkill;
import com.matching.domain.dto.UserDTO;
import com.matching.repository.UserRepository;
import com.matching.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    public StringBuilder validation(BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    public User findUserNick(String nick) {
        return userRepository.findByNick(nick);
    }

    public User findUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> postUser(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder().createdDate(LocalDateTime.now()).email(userDTO.getEmail()).nick(userDTO.getNickname()).
                password(passwordEncoder.encode(userDTO.getPassword())).profileImg(userDTO.getProfileImag()).description(userDTO.getSummary()).
                investTime(userDTO.getInvestTime()).socialUrl(userDTO.getSocialUrl()).build();
        userRepository.save(user);

        for(UserSkill userSkill : userDTO.getUserSkills()) {
            if (userSkill.getText().length() > 255)
                return new ResponseEntity<>("태그의 길이가 255자 이상입니다.", HttpStatus.BAD_REQUEST);
            user.addUserSkill(userSkill);
            userSkillRepository.save(userSkill);
        }

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
}

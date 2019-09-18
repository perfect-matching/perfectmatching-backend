package com.matching.controller;

import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.UserDTO;
import com.matching.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder msg = registerService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        } else if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return new ResponseEntity<>("{\"message\": \"비밀번호가 일치하지 않습니다.\"}", HttpStatus.BAD_REQUEST);
        } else if(registerService.findUserNick(userDTO.getNickname()) != null) {
            return new ResponseEntity<>("{\"message\": \"이미 사용중인 닉네임입니다.\"}", HttpStatus.BAD_REQUEST);
        } else if(registerService.findUserEmail(userDTO.getEmail()) != null) {
            return new ResponseEntity<>("{\"message\": \"이미 사용중인 이메일입니다.\"}", HttpStatus.BAD_REQUEST);
        }

        URI uri = linkTo(methodOn(RegisterController.class).postUser(userDTO, result)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return registerService.postUser(userDTO, restDocs);
    }

    @PostMapping("/nickcheck")
    public ResponseEntity<?> nickCheck(@RequestBody Map<String, String> nick) {
        URI uri = linkTo(methodOn(RegisterController.class).nickCheck(nick)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return registerService.findUserNick(nick.get("nick")) != null ? new ResponseEntity<>("{\"message\": \"이미 사용중인 닉네임입니다.\"}", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("{\"message\": \"사용가능한 멋진 닉네임 입니다.\"}", restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody Map<String, String> email) {
        URI uri = linkTo(methodOn(RegisterController.class).emailCheck(email)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return registerService.findUserEmail(email.get("email")) != null ? new ResponseEntity<>("{\"message\": \"이미 사용중인 이메일입니다.\"}", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("{\"message\": \"사용가능한 멋진 이메일입니다.\"}", restDocs.getHttpHeaders(), HttpStatus.OK);
    }

}

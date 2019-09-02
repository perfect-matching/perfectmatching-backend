package com.matching.controller;

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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDTO userDTO, BindingResult result, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/register");

        if (result.hasErrors()) {
            StringBuilder msg = registerService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        } else if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        } else if(registerService.findUserNick(userDTO.getNickname()) != null) {
            return new ResponseEntity<>("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        } else if(registerService.findUserEmail(userDTO.getEmail()) != null) {
            return new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        return registerService.postUser(userDTO);
    }

    @PostMapping("/nickcheck")
    public ResponseEntity<?> nickCheck(@RequestBody String nick, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/register/nickcheck");;
        return registerService.findUserNick(nick) != null ? new ResponseEntity<>("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("사용가능한 멋진 닉네임입니다.", HttpStatus.OK);
    }

    @PostMapping("/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody String email, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/register/emailcheck");
        return registerService.findUserEmail(email) != null ? new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("사용가능한 멋진 이메일입니다.", HttpStatus.OK);
    }


}

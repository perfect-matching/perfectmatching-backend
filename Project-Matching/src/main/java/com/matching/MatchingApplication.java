package com.matching;

import com.matching.domain.*;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SpringBootApplication
public class MatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchingApplication.class, args);
    }

}

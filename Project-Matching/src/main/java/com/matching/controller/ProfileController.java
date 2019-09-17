package com.matching.controller;

import com.matching.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping(value = "/{idx}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/profile/" + idx);

        if(profileService.findUser(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = profileService.findUserProfile(idx);
        resource.add(linkTo(methodOn(ProfileController.class).getProfile(idx, response)).withSelfRel());
        resource.add(linkTo(methodOn(ProfileController.class).getProfileSkills(idx, response)).withRel("Profile Skills"));
        resource.add(linkTo(methodOn(ProfileController.class).getProfileMyProjects(idx, response)).withRel("My Projects"));
        resource.add(linkTo(methodOn(ProfileController.class).getProfileProjects(idx, response)).withRel("Processing Projects"));
        resource.add(linkTo(methodOn(ProfileController.class).getProfileDoneProjects(idx, response)).withRel("Done Projects"));

        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/{idx}/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileSkills(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/profile/" + idx + "/skills");

        if(profileService.findProfileSkills(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileSkills(idx, response);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileSkills(idx, response)).withSelfRel());
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{idx}/myprojects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileMyProjects(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/profile/" + idx + "/myprojects");

        if(profileService.findByProfileMyProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileMyProjects(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileMyProjects(idx, response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{idx}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileProjects(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/profile/" + idx + "/projects");

        if(profileService.findProfileProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileProjects(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileProjects(idx, response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{idx}/doneprojects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileDoneProjects(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/profile/" + idx + "/doneprojects");

        if(profileService.findProfileDoneProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileDoneProjects(idx, response);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileDoneProjects(idx, response)).withSelfRel());
        return ResponseEntity.ok(resources);
    }
}

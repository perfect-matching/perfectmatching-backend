package com.matching.controller;

import com.matching.domain.docs.RestDocs;
import com.matching.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping(value = "/{idx}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfile(@PathVariable Long idx) {

        if(profileService.findUser(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = profileService.findUserProfile(idx);
        resource.add(linkTo(methodOn(ProfileController.class).getProfile(idx)).withSelfRel());
        resource.add(linkTo(methodOn(ProfileController.class).getProfileSkills(idx)).withRel("Profile Skills"));
        resource.add(linkTo(methodOn(ProfileController.class).getProfileMyProjects(idx)).withRel("My Projects"));
        resource.add(linkTo(methodOn(ProfileController.class).getProfileProjects(idx)).withRel("Processing Projects"));
        resource.add(linkTo(methodOn(ProfileController.class).getProfileDoneProjects(idx)).withRel("Done Projects"));

        URI uri = linkTo(methodOn(ProfileController.class).getProfile(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resource, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idx}/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileSkills(@PathVariable Long idx) {

        if(profileService.findProfileSkills(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileSkills(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileSkills(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProfileController.class).getProfileSkills(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idx}/myprojects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileMyProjects(@PathVariable Long idx) {

        if(profileService.findByProfileMyProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileMyProjects(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileMyProjects(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProfileController.class).getProfileMyProjects(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idx}/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileProjects(@PathVariable Long idx) {

        if(profileService.findProfileProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileProjects(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileProjects(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProfileController.class).getProfileProjects(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/{idx}/doneprojects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileDoneProjects(@PathVariable Long idx) {

        if(profileService.findProfileDoneProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileDoneProjects(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileDoneProjects(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProfileController.class).getProfileDoneProjects(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }
}

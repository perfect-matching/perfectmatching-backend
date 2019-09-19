package com.matching.controller;

import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.ProfileDTO;
import com.matching.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.net.URI;
import java.util.Map;

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
        resource.add(linkTo(methodOn(ProfileController.class).getProfileApplyProjects(idx)).withRel("Apply Projects"));
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

    @GetMapping(value = "/{idx}/applyprojects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProfileApplyProjects(@PathVariable Long idx) {

        if(profileService.findProfileApplyProjects(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = profileService.getProfileApplyProjects(idx);
        resources.add(linkTo(methodOn(ProfileController.class).getProfileApplyProjects(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProfileController.class).getProfileApplyProjects(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);

        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/modify/nickcheck")
    public ResponseEntity<?> nickCheck(@RequestBody Map<String, String> nick, HttpServletRequest request) {
        URI uri = linkTo(methodOn(RegisterController.class).nickCheck(nick)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return profileService.findUserNick(nick.get("nick"), request) ? new ResponseEntity<>("{\"message\": \"이미 사용중인 닉네임입니다.\"}", HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("{\"message\": \"사용가능한 멋진 닉네임 입니다.\"}", restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putProfile(@Valid @RequestBody ProfileDTO profileDTO, BindingResult result, @PathVariable Long idx,
                                        HttpServletRequest request) {
        if (result.hasErrors()) {
            StringBuilder msg = profileService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        URI uri = linkTo(methodOn(ProfileController.class).putProfile(profileDTO, result, idx, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);

        return  profileService.putProfile(profileDTO, request, idx, restDocs);
    }


}

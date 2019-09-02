package com.matching.controller;

import com.matching.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTags(HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/tags");

        if(tagService.findByTags())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = tagService.getTags(response);
        resources.add(linkTo(methodOn(TagController.class).getTags(response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/tag/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTag(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/tag/" + idx);

        if(tagService.findByTag(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = tagService.getTag(idx);
        resource.add(linkTo(methodOn(TagController.class).getTag(idx, response)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/userskills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSkills(HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/userskills");

        if(tagService.findByUserSkills())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = tagService.getUserSkills(response);
        resources.add(linkTo(methodOn(TagController.class).getUserSkills(response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/userskill/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSkill(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/userskill/" + idx);

        if(tagService.findByUserSkill(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = tagService.getUserSkill(idx);
        resource.add(linkTo(methodOn(TagController.class).getUserSkill(idx, response)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/usedskills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsedSkills(HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/usedskills");

        if(tagService.findByUsedSkills())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = tagService.getUsedSkills(response);
        resources.add(linkTo(methodOn(TagController.class).getUsedSkills(response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/usedskill/{idx}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsedSkill(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/usedskill/" + idx);

        if(tagService.findByUsedSkill(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = tagService.getUsedSkill(idx);
        resource.add(linkTo(methodOn(TagController.class).getUsedSkill(idx, response)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

}

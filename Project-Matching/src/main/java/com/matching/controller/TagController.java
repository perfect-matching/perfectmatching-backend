package com.matching.controller;

import com.matching.domain.docs.RestDocs;
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

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTags() {

        if(tagService.findByTags())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = tagService.getTags();
        resources.add(linkTo(methodOn(TagController.class).getTags()).withSelfRel());

        URI uri = linkTo(methodOn(TagController.class).getTags()).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/tag/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTag(@PathVariable Long idx) {

        if(tagService.findByTag(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = tagService.getTag(idx);
        resource.add(linkTo(methodOn(TagController.class).getTag(idx)).withSelfRel());

        URI uri = linkTo(methodOn(TagController.class).getTag(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resource, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/userskills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSkills() {

        if(tagService.findByUserSkills())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = tagService.getUserSkills();
        resources.add(linkTo(methodOn(TagController.class).getUserSkills()).withSelfRel());

        URI uri = linkTo(methodOn(TagController.class).getUserSkills()).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/userskill/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserSkill(@PathVariable Long idx) {

        if(tagService.findByUserSkill(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = tagService.getUserSkill(idx);
        resource.add(linkTo(methodOn(TagController.class).getUserSkill(idx)).withSelfRel());

        URI uri = linkTo(methodOn(TagController.class).getUserSkill(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resource, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/usedskills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsedSkills() {

        if(tagService.findByUsedSkills())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = tagService.getUsedSkills();
        resources.add(linkTo(methodOn(TagController.class).getUsedSkills()).withSelfRel());

        URI uri = linkTo(methodOn(TagController.class).getUserSkills()).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/usedskill/{idx}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsedSkill(@PathVariable Long idx) {

        if(tagService.findByUsedSkill(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = tagService.getUsedSkill(idx);
        resource.add(linkTo(methodOn(TagController.class).getUsedSkill(idx)).withSelfRel());

        URI uri = linkTo(methodOn(TagController.class).getUserSkills()).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resource, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

}

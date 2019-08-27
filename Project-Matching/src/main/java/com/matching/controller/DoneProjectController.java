package com.matching.controller;

import com.matching.service.DoneProjectService;
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
@RequestMapping("/api/doneproject")
public class DoneProjectController {

    @Autowired
    private DoneProjectService doneProjectService;

    @GetMapping(value = "/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDoneProject(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/doneproject/" + idx);

        if(doneProjectService.findByDoneProject(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = doneProjectService.getDoneProject(idx);
        resource.add(linkTo(methodOn(DoneProjectController.class).getDoneProject(idx, response)).withSelfRel());
        resource.add(linkTo(methodOn(DoneProjectController.class).getDoneProjectUsedSkills(idx, response)).withRel("Used Skills"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/{idx}/usedskills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDoneProjectUsedSkills(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/doneproject/" + idx + "/usedskills");

        if(doneProjectService.findByDoneProjectUsedSkills(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = doneProjectService.getDoneProjectUsedSkills(idx, response);
        resources.add(linkTo(methodOn(DoneProjectController.class).getDoneProjectUsedSkills(idx, response)).withSelfRel());
        return ResponseEntity.ok(resources);
    }
}

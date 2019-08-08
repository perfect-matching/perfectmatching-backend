package com.matching.controller;

import com.matching.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getComment(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api//comment/" + idx );

        if(commentService.findComment(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = commentService.getComments(idx);
        resource.add(linkTo(methodOn(CommentController.class).getComment(idx, response)).withSelfRel());
        return ResponseEntity.ok(resource);
    }
}

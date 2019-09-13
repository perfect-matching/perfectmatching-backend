package com.matching.controller;

import com.matching.domain.dto.CommentDTO;
import com.matching.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
        response.setHeader("Location", "/api/comment/" + idx );

        if(commentService.findComment(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = commentService.getComments(idx);
        resource.add(linkTo(methodOn(CommentController.class).getComment(idx, response)).withSelfRel());
        return ResponseEntity.ok(resource);
    }


    @PostMapping
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult result, HttpServletRequest request,
                                         HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/comment" );

        if(result.hasErrors()) {
            StringBuilder msg = commentService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        return commentService.postComment(commentDTO, request);
    }

    @PutMapping(value = "/{idx}")
    public ResponseEntity<?> putComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult result, @PathVariable Long idx,
                                        HttpServletRequest request,HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/comment/" + idx );

        if(result.hasErrors()) {
            StringBuilder msg = commentService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        return commentService.putComment(commentDTO, request, idx);
    }

    @DeleteMapping(value = "/{idx}")
    public ResponseEntity<?> deleteComment(@PathVariable Long idx, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/comment/" + idx );

        return commentService.deleteComment(idx, request);
    }
}

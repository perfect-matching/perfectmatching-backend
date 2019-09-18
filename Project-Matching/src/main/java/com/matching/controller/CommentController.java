package com.matching.controller;

import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.CommentDTO;
import com.matching.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getComment(@PathVariable Long idx) {

        if (commentService.findComment(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = commentService.getComments(idx);
        resource.add(linkTo(methodOn(CommentController.class).getComment(idx)).withSelfRel());

        URI uri = linkTo(methodOn(CommentController.class).getComment(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resource, restDocs.getHttpHeaders(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> postComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult result, HttpServletRequest request) {

            if (result.hasErrors()) {
                StringBuilder msg = commentService.validation(result);
                return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
            }

            URI uri = linkTo(methodOn(CommentController.class).postComment(commentDTO, result, request)).toUri();
            RestDocs restDocs = new RestDocs(uri);
            return commentService.postComment(commentDTO, request, restDocs);
    }


    @PutMapping(value = "/{idx}")
    public ResponseEntity<?> putComment(@Valid @RequestBody CommentDTO commentDTO, BindingResult result, @PathVariable Long idx,
                                        HttpServletRequest request) {

        if (result.hasErrors()) {
            StringBuilder msg = commentService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        URI uri = linkTo(methodOn(CommentController.class).putComment(commentDTO, result, idx, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return commentService.putComment(commentDTO, request, idx, restDocs);
    }

    @DeleteMapping(value = "/{idx}")
    public ResponseEntity<?> deleteComment(@PathVariable Long idx, HttpServletRequest request) {

        URI uri = linkTo(methodOn(CommentController.class).deleteComment(idx, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return commentService.deleteComment(idx, request, restDocs);
    }
}

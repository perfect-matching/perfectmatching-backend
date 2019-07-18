package com.matching.controller;

import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.resource.ProjectResource;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectNotFoundException;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;

import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjects() {

        List<ProjectResource> collection = projectRepository.findAll().stream().map(ProjectResource::new).collect(Collectors.toList());
        Resources<ProjectResource> resources = new Resources<>(collection);
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@PathVariable final long id) {
        return projectRepository
                .findById(id)
                .map(p -> ResponseEntity.ok(new ProjectResource(p)))
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @GetMapping(value = "/{id}/leader", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLeader(@PathVariable final long id) {
        Resource<User> resource = new Resource<>(userRepository.findByIdx(id));
        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resource.add(new Link(uriString, "self"));
        return ResponseEntity.ok(resource);
    }
    

}

package com.matching.controller;

import com.matching.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getProjects() {
//        List<ProjectResource> collection = projectRepository.findAll().stream().map(ProjectResource::new).collect(Collectors.toList());
//        Resources<ProjectResource> resources = new Resources<>(collection);
//
//        return ResponseEntity.ok(resources);
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectsJsonView() {
        Resources<?> resources = projectService.getAllProjects();
        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJsonView(@PathVariable final Long idx) {
        Resource<?> resource = projectService.getProject(idx);
        return ResponseEntity.ok(resource);
    }

}

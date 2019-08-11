package com.matching.controller;

import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.UserProject;
import com.matching.domain.UserProjectKey;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.domain.enums.ProjectStatus;
import com.matching.domain.enums.UserProjectStatus;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserProjectRepository;
import com.matching.repository.UserRepository;
import com.matching.service.ProfileService;
import com.matching.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @GetMapping(value = "/projects",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectsJsonView(@PageableDefault(size = 4) Pageable pageable, HttpServletResponse response,
                                                    @RequestParam(required = false) LocationType location,
                                                    @RequestParam(required = false) String position) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/projects");

        Page<Project> collection = projectService.findAllProject(pageable, location, position);
        if(collection == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Resource> list = projectService.getProjectsDTOList(response, collection);
        Page<?> page = new PageImpl<>(list, pageable, list.size());

        String uriString = projectService.getCurrentUriGetString();
        uriString = projectService.nextUriEncoding(uriString, collection, location, position);

        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), collection.getNumber(), collection.getTotalElements());
        PagedResources<?> resources = new PagedResources<>(page.getContent(), pageMetadata);
        resources.add(new Link(projectService.getCurrentUriGetString(), "self"));
        resources.add(new Link(uriString, "next"));

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/project/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJsonView(@PathVariable final Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx);
        Resource<?> resource = projectService.getProject(idx);
        resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(idx, response)).withSelfRel());
        resource.add(linkTo(methodOn(ProjectController.class).getProjectLeader(idx, response)).withRel("Leader"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectComments(idx, response)).withRel("Comments"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping(value = "/projects/{idx}/leader", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectLeader(@PathVariable final Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx + "/leader");

        if(profileService.findUser(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = profileService.findUserProfile(idx);
        resource.add(linkTo(methodOn(ProfileController.class).getProfile(idx, response)).withSelfRel());

        return ResponseEntity.ok(resource);
    }


    @GetMapping(value = "/project/{idx}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectComments (@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx + "/comments");

        if(projectService.findProjectComments(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectComments(idx, response);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectComments(idx, response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

}

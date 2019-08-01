package com.matching.controller;

import com.matching.domain.Project;
import com.matching.repository.ProjectRepository;
import com.matching.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectsJsonView(@PageableDefault(size = 4) Pageable pageable, HttpServletResponse response,
                                                    @RequestParam(required = false) String location) throws UnsupportedEncodingException {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/projects");

        Page<Project> collection = projectService.findAllProject(pageable, location);
        List<Resource> list = projectService.getProjectsDTOList(response, collection);
        Page<?> page = new PageImpl<>(list, pageable, list.size());

        String uriString = projectService.getCurrentUriGetString();
        uriString = projectService.nextUriEncoding(uriString, collection, location);

        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), collection.getNumber(), collection.getTotalElements());
        PagedResources<?> resources = new PagedResources<>(page.getContent(), pageMetadata);
        resources.add(new Link(projectService.getCurrentUriGetString(), "self"));
        resources.add(new Link(uriString, "next"));

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJsonView(@PathVariable final Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/projects/" + idx);
        Resource<?> resource = projectService.getProject(idx);
        resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(idx, response)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

}

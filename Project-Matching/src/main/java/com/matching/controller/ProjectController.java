package com.matching.controller;

import com.matching.domain.Project;
import com.matching.domain.dto.ProjectApplyDTO;
import com.matching.domain.dto.ProjectDTO;
import com.matching.domain.enums.LocationType;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectsJsonView(@PageableDefault(size = 12) Pageable pageable, HttpServletResponse response,
                                                    @RequestParam(required = false) LocationType location,
                                                    @RequestParam(required = false) String position) {

        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/projects");

        Page<Project> collection = projectService.findAllProject(pageable, location, position);

        if(collection == null)
            return new ResponseEntity<>("잘못된 요청입니다." ,HttpStatus.BAD_REQUEST);

        List<Resource> list = projectService.getProjectsDTOList(response, collection);

        Page<?> page = new PageImpl<>(list, pageable, list.size());

        String uriString = projectService.getCurrentUriGetString();
        uriString = projectService.nextUriEncoding(uriString, collection, location, position);

        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), collection.getNumber(), collection.getTotalElements());
        PagedResources<?> resources = new PagedResources<>(page.getContent(), pageMetadata);

        resources.add(new Link(projectService.getCurrentUriGetString()).withSelfRel());
        resources.add(new Link(uriString).withRel("next"));

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/project/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJsonView(@PathVariable final Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx);

        if(projectService.projectNullCheck(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = projectService.getProject(idx);
        Long leaderIdx = projectService.findByProject(idx).getLeader().getIdx();

        resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(idx, response)).withSelfRel());
        resource.add(linkTo(methodOn(ProfileController.class).getProfile(leaderIdx, response)).withRel("Leader Profile"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectComments(idx, response)).withRel("Comments"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectMembers(idx, response)).withRel("Members"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectJoinMembers(idx, response)).withRel("Join Members"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectTags(idx, response)).withRel("Tags"));
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

    @GetMapping(value = "/project/{idx}/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectMembers(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx + "/members");

        if(projectService.findProjectMembers(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectMembers(idx, response);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectMembers(idx, response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/project/{idx}/joinmembers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJoinMembers(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx + "/joinmembers");

        if(projectService.findProjectJoinMembers(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectJoinMembers(idx, response);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectJoinMembers(idx, response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping(value = "/project/{idx}/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectTags(@PathVariable Long idx, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx + "/tags");

        if(projectService.findProjectTags(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectTags(idx, response);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectTags(idx, response)).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @PostMapping(value = "/project")
    public ResponseEntity<?> postProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project");

        if (result.hasErrors()) {
            StringBuilder msg = projectService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        return projectService.postProject(projectDTO, request);
    }

    @PutMapping(value = "/project/{idx}")
    public ResponseEntity<?> putProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult result, @PathVariable Long idx,
                                        HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx);

        if(result.hasErrors()) {
            StringBuilder msg = projectService.validation(result);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        return projectService.putProject(projectDTO, request, idx);
    }

    @DeleteMapping(value = "/project/{idx}")
    public ResponseEntity<?> deleteProject(@PathVariable Long idx, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx);

        return projectService.deleteProject(idx, request);
    }

    @PutMapping(value = "/project/{idx}/status")
    public ResponseEntity<?> putProjectStatus(@PathVariable Long idx,  @RequestParam(required = false) String status,
                                              HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/" + idx + "/status");

        return projectService.putProjectStatus(idx, status, request);
    }

    @PostMapping(value = "/project/apply")
    public ResponseEntity<?> postProjectApply(@Valid @RequestBody ProjectApplyDTO projectApplyDTO, BindingResult result,
                                              HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/apply");

        if (result.hasErrors()) {
            StringBuilder msg = projectService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        return projectService.postProjectApply(projectApplyDTO, request);
    }

    @PutMapping(value = "/project/matching")
    public ResponseEntity<?> putProjectMatching(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/matching");

        return projectService.putProjectMatching(map, request);
    }

    @DeleteMapping(value = "/project/cancel/{idx}")
    public ResponseEntity<?> deleteProjectCancel(@PathVariable Long idx, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Link", "<https://github.com/perfect-matching/perfectmatching-backend>; rel=\"profile\"");
        response.setHeader("Location", "/api/project/cancel/" + idx);

        return projectService.deleteProjectCancel(idx, request);
    }

}

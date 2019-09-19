package com.matching.controller;

import com.matching.domain.Project;
import com.matching.domain.docs.RestDocs;
import com.matching.domain.dto.ProjectApplyDTO;
import com.matching.domain.dto.ProjectDTO;
import com.matching.domain.enums.LocationType;
import com.matching.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getProjectsJsonView(@PageableDefault(size = 12) Pageable pageable,
                                                    @RequestParam(required = false) LocationType location,
                                                    @RequestParam(required = false) String position,
                                                    @RequestParam(required = false) String tag) {

        Page<Project> collection = projectService.findAllProject(pageable, location, position, tag);

        if(collection == null)
            return new ResponseEntity<>("{\"message\": \"잘못된 요청입니다.\"}" ,HttpStatus.BAD_REQUEST);

        List<Resource> list = projectService.getProjectsDTOList(collection);

        Page<?> page = new PageImpl<>(list, pageable, list.size());

        String uriString = projectService.getCurrentUriGetString();
        uriString = projectService.nextUriEncoding(uriString, collection, location, position);

        PageMetadata pageMetadata = new PageMetadata(pageable.getPageSize(), collection.getNumber(), collection.getTotalElements());
        PagedResources<?> resources = new PagedResources<>(page.getContent(), pageMetadata);

        resources.add(new Link(projectService.getCurrentUriGetString()).withSelfRel());
        resources.add(new Link(uriString).withRel("next"));

        URI uri = linkTo(methodOn(ProjectController.class).getProjectsJsonView(pageable, location, position, tag)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/project/{idx}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJsonView(@PathVariable final Long idx) {

        if(projectService.projectNullCheck(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource<?> resource = projectService.getProject(idx);
        Long leaderIdx = projectService.findByProject(idx).getLeader().getIdx();

        resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(idx)).withSelfRel());
        resource.add(linkTo(methodOn(ProfileController.class).getProfile(leaderIdx)).withRel("Leader Profile"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectComments(idx)).withRel("Comments"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectMembers(idx)).withRel("Members"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectJoinMembers(idx)).withRel("Join Members"));
        resource.add(linkTo(methodOn(ProjectController.class).getProjectTags(idx)).withRel("Tags"));

        URI uri = linkTo(methodOn(ProjectController.class).getProjectJsonView(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resource, restDocs.getHttpHeaders(), HttpStatus.OK);
    }


    @GetMapping(value = "/project/{idx}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectComments (@PathVariable Long idx) {

        if(projectService.findProjectComments(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectComments(idx);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectComments(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProjectController.class).getProjectComments(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/project/{idx}/members", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectMembers(@PathVariable Long idx) {

        if(projectService.findProjectMembers(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectMembers(idx);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectMembers(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProjectController.class).getProjectMembers(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/project/{idx}/joinmembers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectJoinMembers(@PathVariable Long idx) {

        if(projectService.findProjectJoinMembers(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectJoinMembers(idx);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectJoinMembers(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProjectController.class).getProjectJoinMembers(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/project/{idx}/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectTags(@PathVariable Long idx) {

        if(projectService.findProjectTags(idx))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resources<?> resources = projectService.getProjectTags(idx);
        resources.add(linkTo(methodOn(ProjectController.class).getProjectTags(idx)).withSelfRel());

        URI uri = linkTo(methodOn(ProjectController.class).getProjectTags(idx)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>(resources, restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value = "/project")
    public ResponseEntity<?> postProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            StringBuilder msg = projectService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        URI uri = linkTo(methodOn(ProjectController.class).postProject(projectDTO, result, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.postProject(projectDTO, request, restDocs);
    }

    @PutMapping(value = "/project/{idx}")
    public ResponseEntity<?> putProject(@Valid @RequestBody ProjectDTO projectDTO, BindingResult result, @PathVariable Long idx,
                                        HttpServletRequest request) {

        if(result.hasErrors()) {
            StringBuilder msg = projectService.validation(result);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        URI uri = linkTo(methodOn(ProjectController.class).putProject(projectDTO, result, idx, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.putProject(projectDTO, request, idx, restDocs);
    }

    @DeleteMapping(value = "/project/{idx}")
    public ResponseEntity<?> deleteProject(@PathVariable Long idx, HttpServletRequest request) {

        URI uri = linkTo(methodOn(ProjectController.class).deleteProject(idx, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.deleteProject(idx, request, restDocs);
    }

    @PutMapping(value = "/project/{idx}/status")
    public ResponseEntity<?> putProjectStatus(@PathVariable Long idx,  @RequestParam(required = false) String status,
                                              HttpServletRequest request) {

        URI uri = linkTo(methodOn(ProjectController.class).putProjectStatus(idx, status, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.putProjectStatus(idx, status, request, restDocs);
    }

    @PostMapping(value = "/project/apply")
    public ResponseEntity<?> postProjectApply(@Valid @RequestBody ProjectApplyDTO projectApplyDTO, BindingResult result,
                                              HttpServletRequest request) {

        if (result.hasErrors()) {
            StringBuilder msg = projectService.validation(result);
            return new ResponseEntity<>(msg.toString(), HttpStatus.BAD_REQUEST);
        }

        URI uri = linkTo(methodOn(ProjectController.class).postProjectApply(projectApplyDTO, result, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.postProjectApply(projectApplyDTO, request, restDocs);
    }

    @PutMapping(value = "/project/matching")
    public ResponseEntity<?> putProjectMatching(@RequestBody Map<String, String> map, HttpServletRequest request) {

        URI uri = linkTo(methodOn(ProjectController.class).putProjectMatching(map, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.putProjectMatching(map, request, restDocs);
    }

    @DeleteMapping(value = "/project/cancel/{idx}")
    public ResponseEntity<?> deleteProjectCancel(@PathVariable Long idx, HttpServletRequest request) {

        URI uri = linkTo(methodOn(ProjectController.class).deleteProjectCancel(idx, request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return projectService.deleteProjectCancel(idx, request, restDocs);
    }

}

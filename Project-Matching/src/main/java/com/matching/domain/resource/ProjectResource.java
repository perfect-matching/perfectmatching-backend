package com.matching.domain.resource;

import com.matching.controller.ProjectController;
import com.matching.domain.Project;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class ProjectResource extends ResourceSupport {

//    private final Project project;
//
//    public ProjectResource(final Project project) {
//        this.project = project;
//        final long id = project.getIdx();
//
//        add(linkTo(methodOn(ProjectController.class).getLeader(project.getLeader().getIdx())).withRel("leader"));
//        add(linkTo(methodOn(ProjectController.class).get(id)).withSelfRel());
//    }
}

package com.matching.service;

import com.matching.controller.CommentController;
import com.matching.controller.ProfileController;
import com.matching.controller.ProjectController;
import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.UserProject;
import com.matching.domain.dto.CommentDTO;
import com.matching.domain.dto.ProjectDTO;
import com.matching.domain.dto.ProjectsDTO;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Page<Project> findAllProject(Pageable pageable, String location) {
        if(projectRepository.findAllByOrderByIdxDesc(pageable) == null ||
                projectRepository.findByLocationOrderByIdxDesc(location, pageable) == null) {
            return null;
        }
        return location == null ? projectRepository.findAllByOrderByIdxDesc(pageable) : projectRepository.findByLocationOrderByIdxDesc(location, pageable);
    }


    public List<Resource> getProjectsDTOList(HttpServletResponse response, Page<Project> page) {
        List<Resource> list = new ArrayList<>();

        for(Project project : page) {
            ProjectsDTO projectsDTO = new ProjectsDTO(project);
            Resource<?> resource = new Resource<>(projectsDTO);
            resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(project.getIdx(), response)).withSelfRel());
            resource.add(linkTo(methodOn(ProfileController.class).getProfile(project.getLeader().getIdx(), response)).withRel("Leader Profile"));
            list.add(resource);
        }

        return list;
    }

    public Resource<?> getProject(Long idx) {
        Project project = projectRepository.findByIdx(idx);
        ProjectDTO projectDTO = new ProjectDTO(project);
        return new Resource<>(projectDTO);
    }

    public String getCurrentUriGetString() throws UnsupportedEncodingException {
        return URLDecoder.decode(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(), "UTF-8");
    }

    public String nextUriEncoding(String uriString, Page<Project> collection, String location) {
        if(uriString.contains("?offset=" + collection.getNumber()))
            uriString = uriString.replace("?offset=" + collection.getNumber(), "?offset="+ (collection.getNumber() + 1));
        else if(uriString.contains("&offset=" + collection.getNumber()))
            uriString = uriString.replace("&offset=" + collection.getNumber(), "&offset="+ (collection.getNumber() + 1));
        else if(!uriString.contains("&offset=" + collection.getNumber()) && location != null)
            uriString += "&offset="+ (collection.getNumber() + 1);
        else
            uriString += "?offset="+ (collection.getNumber() + 1);

        return uriString;
    }

    public boolean findProjectComments(Long idx) {
        if(projectRepository.findByIdx(idx) == null)
            return true;
        return commentRepository.findByProject(projectRepository.findByIdx(idx)) == null;
    }

    public Resources<?> getProjectComments(Long idx, HttpServletResponse response) {
        List<Resource> list = new ArrayList<>();
        for(Comment comment : commentRepository.findByProject(projectRepository.findByIdx(idx))) {
            CommentDTO commentDTO = new CommentDTO(comment);
            Resource<?> resource = new Resource<>(commentDTO);
            resource.add(linkTo(methodOn(CommentController.class).getComment(comment.getIdx(), response)).withSelfRel());
            list.add(resource);
        }
        return new Resources<>(list);
    }
}
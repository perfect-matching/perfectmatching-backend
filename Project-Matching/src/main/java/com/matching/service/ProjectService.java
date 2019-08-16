package com.matching.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matching.controller.CommentController;
import com.matching.controller.ProfileController;
import com.matching.controller.ProjectController;
import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.UserProject;
import com.matching.domain.dto.CommentDTO;
import com.matching.domain.dto.ProjectDTO;
import com.matching.domain.dto.ProjectsDTO;
import com.matching.domain.enums.LocationType;
import com.matching.domain.enums.PositionType;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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

    @Autowired
    private UserProjectRepository userProjectRepo;

    public Page<Project> findPosition(String position, Pageable pageable) {
        if(position.equals("DEVELOPER"))
            return projectRepository.findByDeveloperRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("DESIGNER"))
            return projectRepository.findByDesignerRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("MARKETER"))
            return projectRepository.findByMarketerRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("PLANNER"))
            return projectRepository.findByPlannerRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        else if(position.equals("ETC"))
            return projectRepository.findByEtcRecruitsIsGreaterThanOrderByIdxDesc(0, pageable);
        return null;
    }

    public Page<Project> findPositionAndLocation(String position, LocationType location, Pageable pageable) {
        if(position.equals("Developer"))
            return projectRepository.findByLocationAndDeveloperRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("DESIGNER"))
            return projectRepository.findByLocationAndDesignerRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("MARKETER"))
            return projectRepository.findByLocationAndMarketerRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("PLANNER"))
            return projectRepository.findByLocationAndPlannerRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        else if(position.equals("ETC"))
            return projectRepository.findByLocationAndEtcRecruitsIsGreaterThanOrderByIdxDesc(location, 0, pageable);
        return null;
    }

    public Page<Project> findAllProject(Pageable pageable, LocationType location, String position) {

        if(position == null && location == null)
            return projectRepository.findAllByOrderByIdxDesc(pageable);
        else if(position == null && location != null)
            return projectRepository.findByLocationOrderByIdxDesc(location, pageable);
        else if(position != null && location == null)
            return findPosition(position, pageable);
        else
            return findPositionAndLocation(position, location, pageable);
    }

    public Project findByProject(Long idx) {
        return projectRepository.findByIdx(idx);
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
        ProjectDTO projectDTO = new ProjectDTO(project, userProjectRepo);
        return new Resource<>(projectDTO);
    }

    public String getCurrentUriGetString() {
        return ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
    }

    public String nextUriEncoding(String uriString, Page<Project> collection, LocationType location, String position) {
        int pageNumber = collection.getNumber();
        if(uriString.contains("?offset=" + pageNumber))
            uriString = uriString.replace("?offset=" + pageNumber, "?offset="+ (pageNumber + 1));
        else if(uriString.contains("&offset=" + pageNumber))
            uriString = uriString.replace("&offset=" + pageNumber, "&offset="+ (pageNumber + 1));
        else if(!uriString.contains("&offset=" + pageNumber) && (location != null || position != null) )
            uriString += "&offset="+ (pageNumber + 1);
        else
            uriString += "?offset="+ (pageNumber + 1);

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
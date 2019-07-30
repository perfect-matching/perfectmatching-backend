package com.matching.service;

import com.matching.controller.ProjectController;
import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.UserProject;
import com.matching.domain.dto.ProjectsDTO;
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

    public Page<Project> findAllProject(Pageable pageable, String location) {
        return location == null ? projectRepository.findAllByOrderByIdxDesc(pageable) : projectRepository.findByLocationOrderByIdxDesc(location, pageable);
    }

//    public Page<Project> findByPositionAndLocation(Pageable pageable) {
//
//    }

    public List<Resource> getProjectsDTOList(HttpServletResponse response, Page<Project> page) {
        List<Resource> list = new ArrayList<>();

        for(Project project : page) {
            ProjectsDTO projectsDTO = new ProjectsDTO(project);
            Resource<?> resource = new Resource<>(projectsDTO);
            resource.add(linkTo(methodOn(ProjectController.class).getProjectJsonView(project.getIdx(), response)).withSelfRel());
            list.add(resource);
        }

        return list;
    }

    public Resource<?> getProject(Long idx) {

        Project project = projectRepository.findByIdx(idx);

        Map<String, Object> map = new HashMap<>();

        map.put("idx", project.getIdx());
        map.put("title", project.getTitle());
        map.put("leader", project.getLeader().getNick());
        map.put("content", project.getContent());
        map.put("location", project.getLocation());
        map.put("createdDate", project.getCreatedDate());
        map.put("deadline", project.getDeadline());
        map.put("startDate", project.getStartDate());
        map.put("endDate", project.getEndDate());
        map.put("modifiedDate", project.getModifiedDate());

        List<Map<String, Object>> userList = new ArrayList<>();
        List<Map<String, Object>> commentList = new ArrayList<>();

        for(Comment comment : project.getComments()) {
            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("작성자", comment.getWriter().getNick());
            commentMap.put("내용", comment.getContent());
            commentMap.put("작성 날짜", comment.getCreatedDate());
            commentList.add(commentMap);
        }

        map.put("comments", commentList);

        for(UserProject userProject : project.getUserProjects()) {
            if(userProject.getStatus().equals("매칭완료")) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("유저네임", userProject.getUser().getNick());
                userMap.put("포지션", userProject.getPosition());
                userList.add(userMap);
            }
        }

        map.put("Project Member", userList);

        return new Resource<>(map);
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
}

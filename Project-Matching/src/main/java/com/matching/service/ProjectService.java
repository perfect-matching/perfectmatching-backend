package com.matching.service;

import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.UserProject;
import com.matching.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Resources<?> getAllProjects() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Project> collection = projectRepository.findAll();

        for(Project project : collection) {
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

            List<String> userList = new ArrayList<>();
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
                userList.add(userProject.getUser().getNick());
            }

            map.put("Project Member", userList);

            list.add(map);
        }

        return new Resources<>(Collections.singleton(list));
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

        List<String> userList = new ArrayList<>();
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
            userList.add(userProject.getUser().getNick());
        }

        map.put("Project Member", userList);

        return new Resource<>(map);
    }
}

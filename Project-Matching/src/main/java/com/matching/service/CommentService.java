package com.matching.service;

import com.matching.domain.Comment;
import com.matching.domain.dto.CommentDTO;
import com.matching.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;


    public boolean findComment(Long idx) {
        return commentRepository.findByIdx(idx) == null;
    }

    public Resource<?> getComments(Long idx) {
        Comment comment = commentRepository.findByIdx(idx);
        CommentDTO commentDTO = new CommentDTO(comment);
        return new Resource<>(commentDTO);
    }
}

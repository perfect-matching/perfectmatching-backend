package com.matching.service;

import com.matching.config.auth.SecurityConstants;
import com.matching.domain.Comment;
import com.matching.domain.Project;
import com.matching.domain.User;
import com.matching.domain.dto.CommentDTO;
import com.matching.repository.CommentRepository;
import com.matching.repository.ProjectRepository;
import com.matching.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean findComment(Long idx) {
        return commentRepository.findByIdx(idx) == null;
    }

    public Resource<?> getComments(Long idx) {
        Comment comment = commentRepository.findByIdx(idx);
        CommentDTO commentDTO = new CommentDTO(comment);
        return new Resource<>(commentDTO);
    }

    public StringBuilder validation(BindingResult result) {
        List<ObjectError> list = result.getAllErrors();
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : list)
            msg.append(error.getDefaultMessage()).append("\n");
        return msg;
    }

    public ResponseEntity<?> postComment(CommentDTO commentDTO, HttpServletRequest request) {

        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""));

        User user = userRepository.findByEmail((String) parsedToken.getBody().get("email"));
        Project project = projectRepository.findByIdx(commentDTO.getProjectIdx());

        Comment comment = Comment.builder().content(commentDTO.getContent()).createdDate(LocalDateTime.now()).build();

        user.addComment(comment);
        project.addComment(comment);
        commentRepository.save(comment);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    public ResponseEntity<?> putComment(CommentDTO commentDTO, HttpServletRequest request, Long idx) {

        Comment comment = commentRepository.findByIdx(idx);

        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""));

        if(!userRepository.findByEmail((String) parsedToken.getBody().get("email")).getIdx().equals(comment.getWriter().getIdx()))
            return new ResponseEntity<>("댓글 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);

        comment.setContent(commentDTO.getContent());
        comment.setModifiedDate(LocalDateTime.now());

        commentRepository.save(comment);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


    public ResponseEntity<?> deleteComment(Long idx, HttpServletRequest request) {
        Comment comment = commentRepository.findByIdx(idx);

        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""));

        if(!userRepository.findByEmail((String) parsedToken.getBody().get("email")).getIdx().equals(comment.getWriter().getIdx()))
            return new ResponseEntity<>("댓글 작성자가 아닙니다.", HttpStatus.BAD_REQUEST);

        commentRepository.deleteById(comment.getIdx());

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}

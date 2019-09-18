package com.matching.controller;

import com.matching.domain.docs.RestDocs;
import com.matching.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/image")
public class FileController {

    @Autowired
    private FileService fileService;

    @PutMapping()
    public ResponseEntity<?> putImage(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        return fileService.uploadFile(file, request);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteImage(HttpServletRequest request) {
        String defaultImageUrl = fileService.setDefaultProfile(request);

        URI uri = linkTo(methodOn(FileController.class).deleteImage(request)).toUri();
        RestDocs restDocs = new RestDocs(uri);
        return new ResponseEntity<>("{\"image\": \"" + defaultImageUrl + "\"}", restDocs.getHttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> getImage(@PathVariable String fileName, HttpServletRequest request) {
        return fileService.loadFileAsResponse(fileName, request);
    }
}

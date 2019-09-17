package com.matching.controller;

import com.matching.domain.FileUploadResponse;
import com.matching.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @PutMapping("/img")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        String fileName = fileService.uploadAndGetName(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/img/")
                .path(fileName)
                .toUriString();

        fileService.userProfileUpdate(fileDownloadUri, request);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/img")
    public ResponseEntity<?> deleteFile(HttpServletRequest request) {
        fileService.setDefaultProfile(request);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping("/img/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

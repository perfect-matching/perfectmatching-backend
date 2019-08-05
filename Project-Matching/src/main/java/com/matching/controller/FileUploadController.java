package com.matching.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/files/multi")
public class FileUploadController {
    private final String UPLOAD_DIR = "/home/ssayebee/UPLOAD_DIR/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAttachment(@RequestPart MultipartFile sourceFile) throws IOException {

        String sourceFileName = sourceFile.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        String destinationFileName;
        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(UPLOAD_DIR + destinationFileName);
        } while (destinationFile.exists());
        destinationFile.getParentFile().mkdirs();
        sourceFile.transferTo(destinationFile);

        UploadAttachmentResponse response = new UploadAttachmentResponse();
        response.setFileName(sourceFile.getOriginalFilename());
        response.setFileSize(sourceFile.getSize());
        response.setFileContentType(sourceFile.getContentType());
        response.setAttachmentUrl("http://localhost:8080/files/multi/upload" + destinationFileName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @NoArgsConstructor
    @Data
    private static class UploadAttachmentResponse {

        private String fileName;

        private long fileSize;

        private String fileContentType;

        private String attachmentUrl;
    }
}

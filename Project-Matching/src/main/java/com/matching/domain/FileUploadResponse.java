package com.matching.domain;

import lombok.Data;

@Data
public class FileUploadResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;

    public FileUploadResponse(String fileName, String fileDownloadUri, String fileType, Long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}

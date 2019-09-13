package com.matching.service;

import com.matching.config.FileConfig;
import com.matching.config.exception.FileDownloadException;
import com.matching.config.exception.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadDownloadService {
    private final Path fileLocation;
    private final String FILE_PREFIX = "PROFILE_IMG_";
    @Autowired
    public FileUploadDownloadService(FileConfig props) {
        this.fileLocation = Paths.get(props.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new FileUploadException("디렉토리 생성 실패.", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String originFileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(originFileName.contains(".."))
                throw new FileUploadException("파일명에 부적합한 문자가 포함되어 있습니다.");
            String extension = FilenameUtils.getExtension(originFileName);
            String fileName = FILE_PREFIX + String.valueOf(System.currentTimeMillis()) + "." + extension;
            Path targetLocation = this.fileLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileUploadException("["+file+"] 파일 업로드 실패, 다시 시도하십시오.", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        } catch (MalformedURLException e) {
            throw new FileDownloadException(fileName + "파일을 찾을 수 없습니다.");
        }
    }
}

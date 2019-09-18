package com.matching.service;

import com.matching.config.FileConfig;
import com.matching.config.auth.JwtResolver;
import com.matching.config.exception.FileUploadException;
import com.matching.controller.FileController;
import com.matching.domain.User;
import com.matching.domain.docs.RestDocs;
import com.matching.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class FileService {
    private final Path fileLocation;
    private static final String FILE_PREFIX = "PROFILE_IMG_";
    private static final String DEFAULT_PROFILE_IMG = "USER_DEFAULT_PROFILE_IMG.png";

    @Autowired
    UserRepository userRepository;

    @Autowired
    public FileService(FileConfig props) {
        this.fileLocation = Paths.get(props.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception e) {
            throw new FileUploadException("디렉토리 생성 실패.", e);
        }
    }

    public ResponseEntity<?> uploadFile(MultipartFile file, HttpServletRequest request) {
        String originFileName = StringUtils.cleanPath(file.getOriginalFilename());

        JwtResolver jwtResolver = new JwtResolver(request);
        User user = userRepository.findByEmail(jwtResolver.getUserByToken());
        String originUrl = user.getProfileImg();

        if (originFileName.contains(".."))
            return new ResponseEntity<>("{\"message\": \"파일에 부적합한 문자가 있습니다.\"}", HttpStatus.BAD_REQUEST);

        String extension = FilenameUtils.getExtension(originFileName);
        String fileName = FILE_PREFIX + System.currentTimeMillis() + "." + extension;
        Path targetLocation = this.fileLocation.resolve(fileName);

        if (!"jpg".equals(extension) && !"jpeg".equals(extension) && !"png".equals(extension)) {
            return new ResponseEntity<>("{\"message\": \"올바르지 않은 확장자 입니다.\"}", HttpStatus.BAD_REQUEST);
        }

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/")
                    .path(fileName)
                    .toUriString();
            deleteOriginProfile(originUrl);
            user.setProfileImg(fileDownloadUri);
            userRepository.save(user);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            RestDocs restDocs = new RestDocs(uri);
            return new ResponseEntity<>("{\"image\": \""+ fileDownloadUri +"\"}", restDocs.getHttpHeaders(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("{\"message\": \"파일에 업로드에 실패했습니다.\"}", HttpStatus.BAD_REQUEST);
        }
    }

    private void deleteOriginProfile(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        if (fileName.equals(DEFAULT_PROFILE_IMG)) return;
        Path path = this.fileLocation.resolve(fileName).normalize();
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<?> loadFileAsResponse(String fileName, HttpServletRequest request) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String contentType = null;
                try {
                    contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                } catch (IOException e) {
                    return new ResponseEntity<>("{\"message\": \"파일을 타입이 올바르지 않습니다.\"}", HttpStatus.BAD_REQUEST);
                }
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                URI uri = linkTo(methodOn(FileController.class).getImage(fileName, request)).toUri();
                RestDocs restDocs = new RestDocs(uri);

                HttpHeaders headers = restDocs.getHttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{\"message\": \"" + fileName + "파일을 찾을 수 없습니다.\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>("{\"message\": \"" + fileName + "파일을 찾을 수 없습니다.\"}", HttpStatus.BAD_REQUEST);
        }

    }

    public String setDefaultProfile(HttpServletRequest request) {
        JwtResolver jwtResolver = new JwtResolver(request);
        String email = jwtResolver.getUserByToken();
        User user = userRepository.findByEmail(email);
        String originUrl = user.getProfileImg();
        deleteOriginProfile(originUrl);

        String defaultImgUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/")
                .path(DEFAULT_PROFILE_IMG)
                .toUriString();
        user.setProfileImg(defaultImgUri);
        userRepository.save(user);
        return defaultImgUri;
    }
}

package com.example.restful.service;

import com.example.restful.exception.FileNotFoundException;
import com.example.restful.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service("fileStorageService")
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    //TODO:待分析为啥注入失败
    //@Autowired
    //public FileStorageService() {
    //    this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    //    try {
    //        Files.createDirectories(this.fileStorageLocation);
    //    } catch (IOException e) {
    //        log.error("Could not create the directory where the uploaded files will be stored.");
    //        throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
    //    }
    //}

    public String storeFile(MultipartFile multipartFile){
        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
}

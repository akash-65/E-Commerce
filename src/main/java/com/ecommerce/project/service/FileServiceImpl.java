package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File name of current/original file
        String originalFileName = file.getOriginalFilename();

        // Generate a unique file name
        String randomID = UUID.randomUUID().toString();
        // Example : mat.jpg --> 1234 -- 1234.jpg
        String fileName = randomID.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        // Check if path exists and create
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // Returning file name
        return fileName;
    }
}

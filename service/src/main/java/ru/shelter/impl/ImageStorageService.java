package ru.shelter.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ImageStorageService {
    @Value("${app.upload.dir}") // Путь из application.properties
    private String uploadDir;

    public List<String> saveImages(MultipartFile[] images) {
        if (images == null || images.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.stream(images)
                .map(this::saveImage)
                .collect(Collectors.toList());
    }

    public String saveImage(MultipartFile image) {
        try
        {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);

            Files.createDirectories(path.getParent());
            image.transferTo(path);

            return "/uploads/" + fileName; // Возвращаем относительный путь
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);

        }
    }

    public boolean validateImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            if (!image.getContentType().startsWith("image/")) {
                log.error("File is not an image, contentType:{}", image.getContentType());
                throw new IllegalArgumentException("Invalid image type");
            }
            if (image.getSize() > 5 * 1024 * 1024) { // 5MB
                throw new IllegalArgumentException("Image size exceeds 5MB");
            }
            return true;
        }
        return false;
    }
}

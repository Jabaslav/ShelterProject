package ru.shelter.impl;

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

@Service
public class ImageStorageImpl {
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
}

package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.validation.AtLeastOneNotNull;

import java.time.LocalDateTime;

// дто для создания поста

@AtLeastOneNotNull(fieldNames = {"description, image"})
public record PostCreateRequestDto (
        @NotEmpty
        Long authorId,

        @Size(max=140)
        String description,

        MultipartFile image
){}

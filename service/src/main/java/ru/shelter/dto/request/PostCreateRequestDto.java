package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.validation.AtLeastOneNotNull;

import java.time.LocalDateTime;

// дто для создания поста
@Data
@AllArgsConstructor
@AtLeastOneNotNull(fieldNames = {"description, image"})
public class PostCreateRequestDto {

    @NotEmpty
    private final Long authorId;

    @Size(max=140)
    private final String description;

    private final MultipartFile image;
}

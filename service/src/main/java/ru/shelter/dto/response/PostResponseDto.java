package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shelter.model.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class PostResponseDto {

    private final Long postId;
    private final Long authorId;
    private final String pictureAddress;
    private final String description;
    private final LocalDateTime creationTime;
}

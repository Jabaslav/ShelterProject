package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shelter.model.User;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class PostResponseDto {

    private  Long postId;
    private  Long authorId;
    private  String pictureAddress;
    private  String description;
    private  LocalDateTime creationTime;
}

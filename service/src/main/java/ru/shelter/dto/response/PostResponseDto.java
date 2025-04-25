package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record PostResponseDto (

    Long postId,
    Long authorId,
    String pictureAddress,
    String description,
    LocalDateTime creationTime
){}

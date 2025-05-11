package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record PostResponse(

    Long id,
    Long authorId,
    String imageAddress,
    String description,
    LocalDateTime creationTime
){}

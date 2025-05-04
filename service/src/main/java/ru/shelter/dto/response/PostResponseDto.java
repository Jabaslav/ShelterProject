package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record PostResponseDto (

    Long id,
    String imageAddress,
    String description,
    LocalDateTime creationTime
){}

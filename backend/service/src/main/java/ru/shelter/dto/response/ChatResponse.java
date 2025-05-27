package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record ChatResponse(
    Long id,
    String name,
    String imageAddress,
    LocalDateTime creationTime
){}

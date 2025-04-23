package ru.shelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


public record ChatResponseDto (
    Long id,
    String name,
    String imageAddress,
    LocalDateTime creationTime
){}

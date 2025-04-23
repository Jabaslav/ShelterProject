package ru.shelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public record PetResponseDto (
    Long id,
    String name,
    String imageAddress,
    String type,
    String description
){}

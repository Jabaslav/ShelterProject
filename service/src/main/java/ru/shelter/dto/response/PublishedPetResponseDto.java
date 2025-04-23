package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

// Дто для запроса на указание питомца в посте

public record PublishedPetResponseDto (
    Long petId,
    Long postId,
    LocalDateTime petAddedDate

){}

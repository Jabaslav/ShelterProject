package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

// Дто для запроса на указание питомца в посте

public record PublishedPetRequestDto (

    @NotEmpty
    Long petId,

    @NotEmpty
    Long postId
){}

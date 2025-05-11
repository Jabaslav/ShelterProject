package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;

// Дто для запроса на указание питомца в посте

public record PublishedPetRequest(

    @NotEmpty
    Long petId,

    @NotEmpty
    Long postId
){}

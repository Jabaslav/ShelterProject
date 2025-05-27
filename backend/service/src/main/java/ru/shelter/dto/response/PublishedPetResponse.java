package ru.shelter.dto.response;

import java.time.LocalDateTime;

// Дто для запроса на указание питомца в посте

public record PublishedPetResponse(
    Long petId,
    Long postId,
    LocalDateTime petAddedDate

){}

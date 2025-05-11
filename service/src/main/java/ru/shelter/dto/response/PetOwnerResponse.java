package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record PetOwnerResponse(
    Long userId,
    Long petId,
    LocalDateTime ownershipCreationTime

){}

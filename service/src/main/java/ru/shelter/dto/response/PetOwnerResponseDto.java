package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record PetOwnerResponseDto (
    Long userId,
    Long petId,
    LocalDateTime ownershipCreationTime

){}

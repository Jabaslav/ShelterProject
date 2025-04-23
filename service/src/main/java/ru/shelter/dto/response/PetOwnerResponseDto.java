package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record PetOwnerResponseDto (
    Long ownerId,
    Long petId,
    LocalDateTime ownershipCreationTime

){}

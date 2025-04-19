package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class PetOwnerResponseDto {
    private final Long ownerId;
    private final Long petId;
    private final LocalDateTime ownershipCreationTime;

}

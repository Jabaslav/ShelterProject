package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

// Дто для запроса на указание питомца в посте
@Data
@AllArgsConstructor
public class PublishedPetResponseDto {
    private final Long petId;
    private final Long postId;
    private final LocalDateTime petAddedDate;

}

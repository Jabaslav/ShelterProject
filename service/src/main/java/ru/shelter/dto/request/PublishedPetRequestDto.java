package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

// Дто для запроса на указание питомца в посте
@Data
@AllArgsConstructor
public class PublishedPetRequestDto {

    @NotEmpty
    private final Long petId;

    @NotEmpty
    private final Long postId;
}

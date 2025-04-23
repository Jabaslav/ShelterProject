package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shelter.validation.AtLeastOneNotNull;

// Дто для запроса на добавление питомца к пользователю (установление владельца)

public record PetOwnerRequestDto (
    @NotEmpty
    Long ownerId,

    @NotEmpty
    Long petId
) {}

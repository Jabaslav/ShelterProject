package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;

// Дто для запроса на добавление питомца к пользователю (установление владельца)

public record PetOwnerRequest(
    @NotEmpty
    Long ownerId,

    @NotEmpty
    Long petId
) {}

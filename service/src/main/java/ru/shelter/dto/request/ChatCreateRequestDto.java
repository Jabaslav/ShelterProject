package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;

// Дто для создания чата

public record ChatCreateRequestDto (

    @NotEmpty
    Long userId,
    @NotEmpty
    String name

){};

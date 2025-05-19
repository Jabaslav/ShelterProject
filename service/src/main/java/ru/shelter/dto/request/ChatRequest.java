package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// Дто для создания чата

public record ChatRequest(

    @NotNull
    Long userId,
    @NotEmpty
    String name

){};

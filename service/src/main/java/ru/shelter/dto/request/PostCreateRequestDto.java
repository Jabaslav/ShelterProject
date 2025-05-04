package ru.shelter.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record PostCreateRequestDto (
        @NotNull(message = "Author ID cannot be null")
        Long userId,

        @NotNull
        @Size(max = 140)
        String description
) {}
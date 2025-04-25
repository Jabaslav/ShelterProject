package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

// дто для создания питомца

public record PetCreateRequestDto (
        @NotEmpty
        @Size(max=30)
        String name,

        @NotEmpty
        @Size(max=30)
        String type,

        @NotEmpty
        @Size(max=140)
        String description
){ }

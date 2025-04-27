package ru.shelter.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.shelter.validation.AtLeastOneNotNull;

@AtLeastOneNotNull(fieldNames = {"description"}) // Теперь проверяет только описание
public record PostCreateRequestDto (
        @NotNull(message = "Author ID cannot be null")
        Long userId,
        @Size(max = 140)
        String description
) {}
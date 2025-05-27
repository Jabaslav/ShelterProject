package ru.shelter.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record PostUpdateRequest(

        @NotNull(message="Описание поста не может быть пустым")
        @Size(max = 140, message = "Максимальное количество символов в посте - 140")
        String description
) {}
package ru.shelter.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record PostRequest(
        @NotNull(message = "Необходимо указать автора поста")
        Long authorId,

        @NotNull(message="Описание поста не может быть пустым")
        @Size(max = 140, message = "Максимальное количество символов в после - 140")
        String description
) {}
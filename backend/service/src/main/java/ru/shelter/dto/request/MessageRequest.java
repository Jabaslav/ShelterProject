package ru.shelter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// Дто для отправки сообщения в чат

public record MessageRequest(
//        @NotEmpty(message="Необходимо указать чат, в которое отправляется сообщение")
//        Long chatId,

        @NotNull(message="Необходимо указать автора сообщения")
        Long authorId,

        @NotBlank(message="Нельзя отправить пустое сообщение")
        String text
){ }

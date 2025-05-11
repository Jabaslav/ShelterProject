package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;

// Дто для отправки сообщения в чат

public record MessageRequest(
//        @NotEmpty(message="Необходимо указать чат, в которое отправляется сообщение")
//        Long chatId,

        @NotEmpty(message="Необходимо указать автора сообщения")
        Long authorId,

        @NotEmpty(message="Нельзя отправить пустое сообщение")
        String text
){ }

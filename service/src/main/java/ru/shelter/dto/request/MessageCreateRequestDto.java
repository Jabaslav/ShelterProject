package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;

// Дто для отправки сообщения в чат

public record MessageCreateRequestDto(
        @NotEmpty
        Long chatId,

        //Айди потом уберем, будем получать из токена
        @NotEmpty
        Long userId,

        @NotEmpty
        String text
){ }

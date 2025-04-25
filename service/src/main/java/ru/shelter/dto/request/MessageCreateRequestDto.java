package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import ru.shelter.validation.AtLeastOneNotNull;

// Дто для отправки сообщения в чат

@AtLeastOneNotNull(fieldNames = {"image, text"})
public record MessageCreateRequestDto(
        @NotEmpty
        Long chatId,

        //Айди потом уберем, будем получать из токена
        @NotEmpty
        Long authorId,

        String text
){ }

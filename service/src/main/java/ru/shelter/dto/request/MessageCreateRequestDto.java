package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.validation.AtLeastOneNotNull;

// Дто для отправки сообщения в чат

@AtLeastOneNotNull(fieldNames = {"image, text"})
public record MessageCreateRequestDto(
        @NotEmpty
        Long chatId,

        //Айди потом уберем, будем получать из токена
        @NotEmpty
        Long authorId,

        MultipartFile image,

        String text
){ }

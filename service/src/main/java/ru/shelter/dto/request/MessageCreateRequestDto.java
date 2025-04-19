package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.validation.AtLeastOneNotNull;

// Дто для отправки сообщения в чат
@Data
@RequiredArgsConstructor
@AtLeastOneNotNull(fieldNames = {"image, text"})
public class MessageCreateRequestDto {

    @NotEmpty
    private final Long chatId;

    //Айди потом уберем, будем получать из токена
    @NotEmpty
    private final Long authorId;

    private final MultipartFile image;

    private final String text;
}

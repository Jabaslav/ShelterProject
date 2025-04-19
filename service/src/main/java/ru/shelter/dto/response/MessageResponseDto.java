package ru.shelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MessageResponseDto {
    private final Long messageId;
    private final Long chatId;
    private final Long authorId;
    private final String imageAddress;
    private final String text;
    private final LocalDateTime creationTime;

}

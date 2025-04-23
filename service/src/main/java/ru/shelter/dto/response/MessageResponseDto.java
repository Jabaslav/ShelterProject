package ru.shelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


public record MessageResponseDto (
    Long messageId,
    Long chatId,
    Long authorId,
    String imageAddress,
    String text,
    LocalDateTime creationTime

){}

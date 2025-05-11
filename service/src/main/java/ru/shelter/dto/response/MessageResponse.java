package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record MessageResponse(
    Long id,
    Long chatId,
    Long authorId,
    String imageAddress,
    String text,
    LocalDateTime creationTime

){}

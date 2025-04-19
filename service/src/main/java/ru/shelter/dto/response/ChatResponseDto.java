package ru.shelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatResponseDto {
    private final Long id;
    private final String name;
    private final String imageAddress;
    private final LocalDateTime creationTime;
}

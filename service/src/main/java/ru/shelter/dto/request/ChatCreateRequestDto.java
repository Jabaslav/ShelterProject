package ru.shelter.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Дто для создания чата (да, чат создается без пользователей, и что вы мне сделаете, это REST)
@Data
@RequiredArgsConstructor
public class ChatCreateRequestDto {
    private final Long chatOwnerId;
    private final String name;
    private final MultipartFile image;
}

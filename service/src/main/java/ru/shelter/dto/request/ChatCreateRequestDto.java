package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Дто для создания чата (да, чат создается без пользователей, и что вы мне сделаете, это REST)

public record ChatCreateRequestDto (

    @NotEmpty
    String name,

     MultipartFile image
){};

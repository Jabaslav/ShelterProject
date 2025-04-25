package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;

// Дто для создания чата (да, чат создается без пользователей, и что вы мне сделаете, это REST)

public record ChatCreateRequestDto (

    @NotEmpty
    String name

){};

package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shelter.model.ChatParticipant;

// Дто для добавления пользователя в чат

public record ChatParticipantRequestDto (
        @NotEmpty
         Long chatId,

        @NotEmpty
        Long userId,

        ChatParticipant.ParticipantRole role
){};

package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import ru.shelter.model.ChatMember;

// Дто для добавления пользователя в чат

public record ChatMemberRequest(
        @NotEmpty
         Long chatId,

        @NotEmpty
        Long userId,

        ChatMember.ParticipantRole role
){};

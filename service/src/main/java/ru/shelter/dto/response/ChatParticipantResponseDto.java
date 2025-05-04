package ru.shelter.dto.response;

import ru.shelter.model.ChatParticipant;


public record ChatParticipantResponseDto (
    //

    Long chatId,
    Long userId,
    ChatParticipant.ParticipantRole role,
    Boolean isActive

){}

package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shelter.model.ChatParticipant;


public record ChatParticipantResponseDto (
    //

    Long chatId,
    Long userId,
    ChatParticipant.ParticipantRole role,
    Boolean isActive

){}

package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shelter.model.ChatParticipant;

@Data
@AllArgsConstructor
public class ChatParticipantResponseDto {
    //

    private final Long chatId;
    private final Long userId;
    private final ChatParticipant.ParticipantRole role;
    private final Boolean isActive;

}

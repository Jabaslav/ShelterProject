package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shelter.model.ChatParticipant;

// Дто для добавления пользователя в чат
@Data
@AllArgsConstructor
public class ChatParticipantRequestDto {

    @NotEmpty
    private final Long chatId;

    @NotEmpty
    private final Long userId;

    private final ChatParticipant.ParticipantRole role;
    
}

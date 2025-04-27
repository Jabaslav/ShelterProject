package ru.shelter.serviceInterfaces;


import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatParticipantResponseDto;
import ru.shelter.dto.response.ChatResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.model.ChatParticipantId;

import java.util.List;

public interface ChatParticipantService extends ServiceInterface<ChatParticipantRequestDto, ChatParticipantResponseDto, ChatParticipantId> {
    List<UserResponseDto> findUserByChatId(Long chatId);
    List<ChatResponseDto> findChatByUserId(Long userId);
}

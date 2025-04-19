package ru.shelter.Interfaces;


import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatParticipantResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.model.ChatParticipantId;

import java.util.ArrayList;

public interface ChatParticipantService extends ServiceInterface<ChatParticipantRequestDto, ChatParticipantResponseDto, ChatParticipantId> {
    ArrayList<UserResponseDto> findUserByChatId(Long chatId);
    ArrayList<ChatDao> findChatByUserId(Long userId);
}

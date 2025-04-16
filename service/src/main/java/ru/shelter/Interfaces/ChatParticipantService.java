package ru.shelter.Interfaces;

import ru.shelter.dto.ChatParticipantDto;
import ru.shelter.dto.UserDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.model.ChatParticipantId;

import java.util.ArrayList;

public interface ChatParticipantService extends ServiceInterface<ChatParticipantDto, ChatParticipantId> {
    ArrayList<UserDto> findUserByChatId(Long chatId);
    ArrayList<ChatDao> findChatByUserId(Long userId);
}

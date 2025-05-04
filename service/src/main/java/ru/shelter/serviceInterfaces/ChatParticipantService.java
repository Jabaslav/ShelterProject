package ru.shelter.serviceInterfaces;


import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatParticipantResponseDto;
import ru.shelter.model.ChatParticipantId;

import java.util.List;

public interface ChatParticipantService extends ServiceInterface<ChatParticipantRequestDto, ChatParticipantResponseDto, ChatParticipantId> {
    List<ChatParticipantResponseDto> findUsersByChatId(Long chatId);
    List<ChatParticipantResponseDto> findChatsByUserId(Long userId);

    ChatParticipantResponseDto add(ChatParticipantRequestDto requestDto);

    ChatParticipantResponseDto update(ChatParticipantRequestDto requestDto, ChatParticipantId chatParticipantId);
}

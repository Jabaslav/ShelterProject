package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.ChatParticipantService;
import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatParticipantResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.model.ChatParticipantId;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatParticipantImpl implements ChatParticipantService {

    @Override
    public ArrayList<UserResponseDto> findUserByChatId(Long chatId) {
        return null;
    }

    @Override
    public ArrayList<ChatDao> findChatByUserId(Long userId) {
        return null;
    }

    @Override
    public ChatParticipantResponseDto add(ChatParticipantRequestDto Object) {
        return null;
    }

    @Override
    public Optional<ChatParticipantResponseDto> get(ChatParticipantId chatParticipantId) {
        return Optional.empty();
    }

    @Override
    public ChatParticipantResponseDto update(ChatParticipantRequestDto Object, ChatParticipantId chatParticipantId) {
        return null;
    }

    @Override
    public boolean remove(ChatParticipantId chatParticipantId) {
        return false;
    }

    @Override
    public ArrayList<ChatParticipantResponseDto> getAll() {
        return null;
    }
}

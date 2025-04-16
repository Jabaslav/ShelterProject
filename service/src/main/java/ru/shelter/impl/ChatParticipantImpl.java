package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.ChatParticipantService;
import ru.shelter.dto.ChatParticipantDto;
import ru.shelter.dto.UserDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.model.ChatParticipantId;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatParticipantImpl implements ChatParticipantService {
    @Override
    public ArrayList<UserDto> findUserByChatId(Long chatId) {
        return null;
    }

    @Override
    public ArrayList<ChatDao> findChatByUserId(Long userId) {
        return null;
    }

    @Override
    public ChatParticipantDto add(ChatParticipantDto object) {
        return null;
    }

    @Override
    public Optional<ChatParticipantDto> get(ChatParticipantId id) {
        return Optional.empty();
    }

    @Override
    public boolean update(ChatParticipantDto Object, ChatParticipantId id) {
        return false;
    }

    @Override
    public boolean remove(ChatParticipantId chatParticipantId) {
        return false;
    }

    @Override
    public ArrayList<ChatParticipantDto> getAll() {
        return null;
    }
}

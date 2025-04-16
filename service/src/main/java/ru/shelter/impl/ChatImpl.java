package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.ChatService;
import ru.shelter.dto.ChatDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatImpl implements ChatService {
    @Override
    public ChatDto add(ChatDto object) {
        return null;
    }

    @Override
    public Optional<ChatDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(ChatDto object, Long id) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public ArrayList<ChatDto> getAll() {
        return null;
    }
}

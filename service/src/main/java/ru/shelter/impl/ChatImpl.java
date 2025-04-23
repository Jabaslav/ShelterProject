package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.ChatService;
import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.response.ChatResponseDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatImpl implements ChatService {

    @Override
    public ChatResponseDto add(ChatCreateRequestDto Object) {
        return null;
    }

    @Override
    public Optional<ChatResponseDto> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public ChatResponseDto update(ChatCreateRequestDto Object, Long aLong) {
        return null;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public ArrayList<ChatResponseDto> getAll() {
        return null;
    }
}

package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.MessageService;
import ru.shelter.dto.request.MessageCreateRequestDto;

import ru.shelter.dto.response.MessageResponseDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MessageImpl implements MessageService {


    @Override
    public MessageResponseDto add(MessageCreateRequestDto Object) {
        return null;
    }

    @Override
    public Optional<MessageResponseDto> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean update(MessageCreateRequestDto Object, Long aLong) {
        return false;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public ArrayList<MessageResponseDto> getAll() {
        return null;
    }
}

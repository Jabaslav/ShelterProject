package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.MessageService;
import ru.shelter.dto.MessageDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MessageImpl implements MessageService {
    @Override
    public MessageDto add(MessageDto object) {
        return null;
    }

    @Override
    public Optional<MessageDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(MessageDto Object, Long id) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public ArrayList<MessageDto> getAll() {
        return null;
    }
}

package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.PetService;
import ru.shelter.Interfaces.ServiceInterface;
import ru.shelter.dto.PetDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PetImpl implements PetService {
    @Override
    public PetDto add(PetDto Object) {
        return null;
    }

    @Override
    public Optional<PetDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(PetDto Object, Long id) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public ArrayList<PetDto> getAll() {
        return null;
    }
}

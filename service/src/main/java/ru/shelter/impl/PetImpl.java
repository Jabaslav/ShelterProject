package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.PetService;
import ru.shelter.dto.request.PetCreateRequestDto;
import ru.shelter.dto.response.PetResponseDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PetImpl implements PetService {

    @Override
    public PetResponseDto add(PetCreateRequestDto Object) {
        return null;
    }

    @Override
    public Optional<PetResponseDto> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public PetResponseDto update(PetCreateRequestDto Object, Long aLong) {
        return null;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public ArrayList<PetResponseDto> getAll() {
        return null;
    }
}

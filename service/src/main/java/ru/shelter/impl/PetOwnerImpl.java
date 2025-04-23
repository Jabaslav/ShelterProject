package ru.shelter.impl;

import ru.shelter.Interfaces.PetOwnerService;
import ru.shelter.dto.request.PetOwnerRequestDto;
import ru.shelter.dto.response.PetOwnerResponseDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.PetOwnerId;

import java.util.ArrayList;
import java.util.Optional;

public class PetOwnerImpl implements PetOwnerService {


    @Override
    public ArrayList<PetResponseDto> findPetByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public ArrayList<UserResponseDto> findOwnerByPetId(Long petId) {
        return null;
    }

    @Override
    public PetOwnerResponseDto add(PetOwnerRequestDto Object) {
        return null;
    }

    @Override
    public Optional<PetOwnerResponseDto> get(PetOwnerId petOwnerId) {
        return Optional.empty();
    }

    @Override
    public PetOwnerResponseDto update(PetOwnerRequestDto Object, PetOwnerId petOwnerId) {
        return null;
    }

    @Override
    public boolean remove(PetOwnerId petOwnerId) {
        return false;
    }

    @Override
    public ArrayList<PetOwnerResponseDto> getAll() {
        return null;
    }
}

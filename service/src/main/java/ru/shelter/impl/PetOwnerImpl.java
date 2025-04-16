package ru.shelter.impl;

import ru.shelter.Interfaces.PetOwnerService;
import ru.shelter.dto.PetDto;
import ru.shelter.dto.PetOwnerDto;
import ru.shelter.dto.UserDto;
import ru.shelter.model.PetOwnerId;

import java.util.ArrayList;
import java.util.Optional;

public class PetOwnerImpl implements PetOwnerService {
    @Override
    public ArrayList<PetDto> findPetByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public ArrayList<UserDto> findOwnerByPetId(Long petId) {
        return null;
    }

    @Override
    public PetOwnerDto add(PetOwnerDto object) {
        return null;
    }

    @Override
    public Optional<PetOwnerDto> get(PetOwnerId petOwnerId) {
        return Optional.empty();
    }

    @Override
    public boolean update(PetOwnerDto Object, PetOwnerId petOwnerId) {
        return false;
    }

    @Override
    public boolean remove(PetOwnerId petOwnerId) {
        return false;
    }

    @Override
    public ArrayList<PetOwnerDto> getAll() {
        return null;
    }
}

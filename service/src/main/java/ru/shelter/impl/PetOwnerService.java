package ru.shelter.impl;

import ru.shelter.dto.response.PetResponse;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.serviceInterfaces.PetOwnerInterface;
import ru.shelter.dto.request.PetOwnerRequest;
import ru.shelter.dto.response.PetOwnerResponse;
import ru.shelter.model.PetOwnerId;

import java.util.ArrayList;
import java.util.Optional;

public class PetOwnerService implements PetOwnerInterface {


    @Override
    public ArrayList<PetResponse> findPetByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public ArrayList<UserResponse> findOwnerByPetId(Long petId) {
        return null;
    }

    @Override
    public PetOwnerResponse add(PetOwnerRequest Object) {
        return null;
    }

    @Override
    public Optional<PetOwnerResponse> get(PetOwnerId petOwnerId) {
        return Optional.empty();
    }

    @Override
    public PetOwnerResponse update(PetOwnerRequest Object, PetOwnerId petOwnerId) {
        return null;
    }

    @Override
    public boolean remove(PetOwnerId petOwnerId) {
        return false;
    }

    @Override
    public ArrayList<PetOwnerResponse> getAll() {
        return null;
    }
}

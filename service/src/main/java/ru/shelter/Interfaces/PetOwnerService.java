package ru.shelter.Interfaces;

import ru.shelter.dto.PetDto;
import ru.shelter.dto.PetOwnerDto;
import ru.shelter.dto.UserDto;
import ru.shelter.model.PetOwnerId;

import java.util.ArrayList;

public interface PetOwnerService extends ServiceInterface<PetOwnerDto, PetOwnerId> {

    ArrayList<PetDto> findPetByOwnerId(Long ownerId);
    ArrayList<UserDto> findOwnerByPetId(Long petId);
}

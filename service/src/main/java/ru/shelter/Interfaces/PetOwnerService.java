package ru.shelter.Interfaces;


import ru.shelter.dto.request.PetOwnerRequestDto;
import ru.shelter.dto.response.PetOwnerResponseDto;
import ru.shelter.dto.response.PetResponseDto;

import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.PetOwnerId;

import java.util.ArrayList;

public interface PetOwnerService extends ServiceInterface<PetOwnerRequestDto, PetOwnerResponseDto, PetOwnerId> {

    ArrayList<PetResponseDto> findPetByOwnerId(Long ownerId);
    ArrayList<UserResponseDto> findOwnerByPetId(Long petId);
}

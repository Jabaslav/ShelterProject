package ru.shelter.serviceInterfaces;


import ru.shelter.dto.request.PetOwnerRequestDto;
import ru.shelter.dto.response.PetOwnerResponseDto;
import ru.shelter.dto.response.PetResponseDto;

import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.PetOwnerId;

import java.util.List;

public interface PetOwnerService extends ServiceInterface<PetOwnerRequestDto, PetOwnerResponseDto, PetOwnerId> {

    List<PetResponseDto> findPetByOwnerId(Long ownerId);
    List<UserResponseDto> findOwnerByPetId(Long petId);

    PetOwnerResponseDto add(PetOwnerRequestDto Object);

    PetOwnerResponseDto update(PetOwnerRequestDto Object, PetOwnerId petOwnerId);
}

package ru.shelter.serviceInterfaces;


import ru.shelter.dto.request.PetOwnerRequest;
import ru.shelter.dto.response.PetOwnerResponse;
import ru.shelter.dto.response.PetResponse;

import ru.shelter.dto.response.UserResponse;
import ru.shelter.model.PetOwnerId;

import java.util.List;

public interface PetOwnerInterface extends ServiceInterface<PetOwnerRequest, PetOwnerResponse, PetOwnerId> {

    List<PetResponse> findPetByOwnerId(Long ownerId);
    List<UserResponse> findOwnerByPetId(Long petId);

    PetOwnerResponse add(PetOwnerRequest Object);

    PetOwnerResponse update(PetOwnerRequest Object, PetOwnerId petOwnerId);
}

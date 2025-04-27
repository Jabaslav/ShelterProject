package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.PublishedPetRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.dto.response.PublishedPetResponseDto;
import ru.shelter.interfaces.PetDao;
import ru.shelter.interfaces.PostDao;
import ru.shelter.model.PublishedPetId;

import java.util.List;

public interface PublishedPetService extends ServiceInterface<PublishedPetRequestDto, PublishedPetResponseDto, PublishedPetId> {
    List<PetResponseDto> findPetByPost (Long postId);
    List<PostResponseDto> findPostByPet(Long petId);
}

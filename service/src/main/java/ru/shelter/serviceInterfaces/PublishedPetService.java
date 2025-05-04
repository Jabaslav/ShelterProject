package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.PublishedPetRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.dto.response.PublishedPetResponseDto;
import ru.shelter.model.PublishedPetId;

import java.util.List;

public interface PublishedPetService extends ServiceInterface<PublishedPetRequestDto, PublishedPetResponseDto, PublishedPetId> {
    List<PetResponseDto> findPetsByPost (Long postId);
    List<PostResponseDto> findPostsByPet(Long petId);

    public PublishedPetResponseDto update(PublishedPetRequestDto Object, PublishedPetId publishedPetId);
    PublishedPetResponseDto add(PublishedPetRequestDto Object);
}

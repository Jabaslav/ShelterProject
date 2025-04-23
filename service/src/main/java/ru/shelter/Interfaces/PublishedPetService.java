package ru.shelter.Interfaces;

import ru.shelter.dto.request.PublishedPetRequestDto;
import ru.shelter.dto.response.PublishedPetResponseDto;
import ru.shelter.interfaces.PetDao;
import ru.shelter.interfaces.PostDao;
import ru.shelter.model.PublishedPet;
import ru.shelter.model.PublishedPetId;

import java.util.ArrayList;
import java.util.List;

public interface PublishedPetService extends ServiceInterface<PublishedPetRequestDto, PublishedPetResponseDto, PublishedPetId> {
    List<PetDao> findPetByPost (Long postId);
    List<PostDao> findPostByPet(Long petId);
}

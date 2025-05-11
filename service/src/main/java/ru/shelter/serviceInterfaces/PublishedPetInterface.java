package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.PublishedPetRequest;
import ru.shelter.dto.response.PetResponse;
import ru.shelter.dto.response.PostResponse;
import ru.shelter.dto.response.PublishedPetResponse;
import ru.shelter.model.PublishedPetId;

import java.util.List;

public interface PublishedPetInterface extends ServiceInterface<PublishedPetRequest, PublishedPetResponse, PublishedPetId> {
    List<PetResponse> findPetsByPost (Long postId);
    List<PostResponse> findPostsByPet(Long petId);

    public PublishedPetResponse update(PublishedPetRequest Object, PublishedPetId publishedPetId);
    PublishedPetResponse add(PublishedPetRequest Object);
}

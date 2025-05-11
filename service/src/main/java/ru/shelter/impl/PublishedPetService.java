package ru.shelter.impl;

import ru.shelter.dto.response.PetResponse;
import ru.shelter.dto.response.PostResponse;
import ru.shelter.serviceInterfaces.PublishedPetInterface;
import ru.shelter.dto.request.PublishedPetRequest;
import ru.shelter.dto.response.PublishedPetResponse;
import ru.shelter.model.PublishedPetId;

import java.util.List;
import java.util.Optional;

public class PublishedPetService implements PublishedPetInterface {

    @Override
    public List<PetResponse> findPetsByPost(Long postId) {
        return null;
    }

    @Override
    public List<PostResponse> findPostsByPet(Long petId) {
        return null;
    }

    @Override
    public PublishedPetResponse add(PublishedPetRequest Object) {
        return null;
    }

    @Override
    public Optional<PublishedPetResponse> get(PublishedPetId publishedPetId) {
        return Optional.empty();
    }

    @Override
    public PublishedPetResponse update(PublishedPetRequest Object, PublishedPetId publishedPetId) {
        return null;
    }

    @Override
    public boolean remove(PublishedPetId publishedPetId) {
        return false;
    }

    @Override
    public List<PublishedPetResponse> getAll() {
        return null;
    }
}

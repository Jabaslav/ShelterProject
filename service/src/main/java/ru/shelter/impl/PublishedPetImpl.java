package ru.shelter.impl;

import ru.shelter.Interfaces.PublishedPetService;
import ru.shelter.dto.request.PublishedPetRequestDto;
import ru.shelter.dto.response.PublishedPetResponseDto;
import ru.shelter.interfaces.PetDao;
import ru.shelter.interfaces.PostDao;
import ru.shelter.model.PublishedPet;
import ru.shelter.model.PublishedPetId;

import java.util.ArrayList;
import java.util.Optional;

public class PublishedPetImpl implements PublishedPetService {

    @Override
    public ArrayList<PetDao> findPetByPost(Long postId) {
        return null;
    }

    @Override
    public ArrayList<PostDao> findPostByPet(Long petId) {
        return null;
    }

    @Override
    public PublishedPetResponseDto add(PublishedPetRequestDto Object) {
        return null;
    }

    @Override
    public Optional<PublishedPetResponseDto> get(PublishedPetId publishedPetId) {
        return Optional.empty();
    }

    @Override
    public boolean update(PublishedPetRequestDto Object, PublishedPetId publishedPetId) {
        return false;
    }

    @Override
    public boolean remove(PublishedPetId publishedPetId) {
        return false;
    }

    @Override
    public ArrayList<PublishedPetResponseDto> getAll() {
        return null;
    }
}

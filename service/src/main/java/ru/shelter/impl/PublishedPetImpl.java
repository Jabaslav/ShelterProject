package ru.shelter.impl;

import ru.shelter.Interfaces.PublishedPetService;
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
    public PublishedPet add(PublishedPet object) {
        return null;
    }

    @Override
    public Optional<PublishedPet> get(PublishedPetId publishedPetId) {
        return Optional.empty();
    }

    @Override
    public boolean update(PublishedPet Object, PublishedPetId publishedPetId) {
        return false;
    }

    @Override
    public boolean remove(PublishedPetId publishedPetId) {
        return false;
    }

    @Override
    public ArrayList<PublishedPet> getAll() {
        return null;
    }
}

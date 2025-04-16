package ru.shelter.Interfaces;

import ru.shelter.interfaces.PetDao;
import ru.shelter.interfaces.PostDao;
import ru.shelter.model.PublishedPet;
import ru.shelter.model.PublishedPetId;

import java.util.ArrayList;

public interface PublishedPetService extends ServiceInterface<PublishedPet, PublishedPetId> {
    ArrayList<PetDao> findPetByPost (Long postId);
    ArrayList<PostDao> findPostByPet(Long petId);
}

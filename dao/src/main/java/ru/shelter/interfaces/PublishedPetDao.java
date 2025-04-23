package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.PublishedPet;
import ru.shelter.model.PublishedPetId;


@Repository
public interface PublishedPetDao extends JpaRepository<PublishedPet, PublishedPetId> {
}

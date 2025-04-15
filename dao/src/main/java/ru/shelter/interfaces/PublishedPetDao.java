package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.PublishedPet;


@Repository
public interface PublishedPetDao extends JpaRepository<PublishedPet, PublishedPet.PublishedPetId> {
}

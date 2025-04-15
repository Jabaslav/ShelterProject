package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.Pet;

@Repository
public interface PetDao extends JpaRepository<Pet, Long> {
}

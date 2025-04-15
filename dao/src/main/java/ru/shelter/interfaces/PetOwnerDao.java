package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.PetOwner;

@Repository
public interface PetOwnerDao extends JpaRepository<PetOwner, PetOwner.PetOwnerId> {

}

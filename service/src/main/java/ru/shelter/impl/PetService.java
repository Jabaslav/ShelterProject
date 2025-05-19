package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetRequest;
import ru.shelter.dto.response.PetResponse;
import ru.shelter.serviceInterfaces.PetInterface;
import ru.shelter.interfaces.PetRepo;
import ru.shelter.mapper.PetMapper;
import ru.shelter.model.Pet;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService implements PetInterface {
    private final PetRepo petRepo;
    private final PetMapper petMapper;
    private final ImageStorageService imageStorage;

    @Override
    public PetResponse add(PetRequest requestDto, MultipartFile image) {
        try {
            Pet pet = petMapper.fromDto(requestDto);
            if (imageStorage.validateImage(image))
                pet.setImageAddress(imageStorage.saveImage(image));
            Pet savedPet = petRepo.save(pet);
            log.info("Added pet: ID {}", savedPet.getId());
            return petMapper.toPetResponse(savedPet);
        } catch (Exception e) {
            log.error("Error adding pet: {}", e.getMessage());
            throw new RuntimeException("Failed to create pet");
        }
    }


    @Override
    public Optional<PetResponse> get(Long id) {
        try {
            Optional<Pet> pet = petRepo.findById(id);
            pet.ifPresentOrElse(
                    p -> log.info("Retrieved pet: ID {}", p.getId()),
                    () -> log.warn("Pet with ID {} not found", id)
            );
            return pet.map(petMapper::toPetResponse);
        } catch (Exception e) {
            log.error("Error retrieving pet: ID {}", id);
            return Optional.empty();
        }
    }


    public PetResponse update(PetRequest requestDto, Long id, MultipartFile image) {
        return petRepo.findById(id)
                .map(existingPet -> {
                    petMapper.updateFromDto(requestDto, existingPet);
                    petRepo.save(existingPet);
                    log.info("Updated pet: ID {}", id);
                    return petMapper.toPetResponse(existingPet);
                })
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public boolean remove(Long id) {
        try {
            petRepo.deleteById(id);
            log.info("Deleted pet: ID {}", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Pet with ID {} not found", id);
            throw e;
        }
    }

    @Override
    public List<PetResponse> getAll() {
        try {
            List<Pet> pets = petRepo.findAll();
            log.info("Retrieved {} pets", pets.size());
            return petMapper.toPetResponseList(pets);
        } catch (Exception e) {
            log.error("Error retrieving pets: {}", e.getMessage());
            throw e;
        }
    }
}

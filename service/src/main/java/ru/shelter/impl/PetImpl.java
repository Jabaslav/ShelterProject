package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.serviceInterfaces.PetService;
import ru.shelter.dto.request.PetCreateRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.interfaces.PetDao;
import ru.shelter.mapper.PetMapper;
import ru.shelter.model.Pet;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetImpl implements PetService {
    private final PetDao petDao;
    private final PetMapper petMapper;
    private final ImageStorageImpl imageStorage;

    @Override
    @Transactional
    public PetResponseDto add(PetCreateRequestDto requestDto) {
        try {
            Pet pet = petMapper.fromDto(requestDto);
            Pet savedPet = petDao.save(pet);
            log.info("Added pet: ID {}", savedPet.getId());
            return petMapper.toPetResponse(savedPet);
        } catch (Exception e) {
            log.error("Error adding pet: {}", e.getMessage());
            throw new RuntimeException("Failed to create pet");
        }
    }

    @Override
    @Transactional
    public PetResponseDto addWithImage(PetCreateRequestDto requestDto, MultipartFile image) {
        validateImage(image);
        try {
            Pet pet = petMapper.fromDto(requestDto);
            pet.setImageAddress(imageStorage.saveImage(image));
            Pet savedPet = petDao.save(pet);
            log.info("Added pet with image: ID {}", savedPet.getId());
            return petMapper.toPetResponse(savedPet);
        } catch (Exception e) {
            log.error("Error adding pet with image: {}", e.getMessage());
            throw new RuntimeException("Failed to create pet with image");
        }
    }

    private void validateImage(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            if (!image.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Invalid image type");
            }
            if (image.getSize() > 5 * 1024 * 1024) { // 5MB
                throw new IllegalArgumentException("Image size exceeds 5MB");
            }
        }
    }

    @Override
    public Optional<PetResponseDto> get(Long id) {
        try {
            Optional<Pet> pet = petDao.findById(id);
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

    @Override
    @Transactional
    public PetResponseDto update(PetCreateRequestDto requestDto, Long id) {
        return petDao.findById(id)
                .map(existingPet -> {
                    petMapper.updateFromDto(requestDto, existingPet);
                    petDao.save(existingPet);
                    log.info("Updated pet: ID {}", id);
                    return petMapper.toPetResponse(existingPet);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public PetResponseDto updateWithImage(PetCreateRequestDto requestDto, Long id, MultipartFile image) {
        validateImage(image);
        return petDao.findById(id)
                .map(existingPet -> {
                    petMapper.updateFromDto(requestDto, existingPet);
                    existingPet.setImageAddress(imageStorage.saveImage(image));
                    petDao.save(existingPet);
                    log.info("Updated pet with image: ID {}", id);
                    return petMapper.toPetResponse(existingPet);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean remove(Long id) {
        try {
            petDao.deleteById(id);
            log.info("Deleted pet: ID {}", id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("Pet with ID {} not found", id);
            return false;
        }
    }

    @Override
    public List<PetResponseDto> getAll() {
        try {
            List<Pet> pets = petDao.findAll();
            log.info("Retrieved {} pets", pets.size());
            return petMapper.toPetResponseList(pets);
        } catch (Exception e) {
            log.error("Error retrieving pets: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}

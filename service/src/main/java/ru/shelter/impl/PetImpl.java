package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.Interfaces.PetService;
import ru.shelter.dto.request.PetCreateRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.interfaces.PetDao;
import ru.shelter.mapper.PetMapper;
import ru.shelter.model.Pet;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PetImpl implements PetService {

    private final PetDao petDao;
    private final PetMapper petMapper;
    private final ImageStorageImpl imageStorage;

    @Override
    public PetResponseDto add(PetCreateRequestDto requestDto) {
        try {
            Pet pet = petMapper.fromDto(requestDto); // создание entity через маппер
            Pet savedPet = petDao.save(pet); // сохраняем питомца в бд
            PetResponseDto response = petMapper.toPetResponse(savedPet); // готовим ответ на запрос
            log.info("Adding pet: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add Pet error:", e);
        };
        return null;
    }

    @Override
    public PetResponseDto addWithImage(PetCreateRequestDto requestDto, MultipartFile image) {
        try {
            Pet pet = petMapper.fromDto(requestDto); // создание entity через маппер
            if (image!=null && !image.isEmpty())
                pet.setImageAddress(imageStorage.saveImage(image));
            petDao.save(pet); // сохраняем питомца в бд
            PetResponseDto response = petMapper.toPetResponse(pet); // готовим ответ на запрос
            log.info("Adding pet: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add Pet error:", e);
        };
        return null;
    }



    @Override
    public Optional<PetResponseDto> get(Long id) {
        try {
            Optional<PetResponseDto> response = petDao.findById(id).map(petMapper::toPetResponse);
            if (response.isPresent())
                log.info("Get pet:", response);
            else
                log.info("Pet with id", id, "not found");
            return response;
        }
        catch (Exception e)
        {
            log.error ("Get pet error:", e);
            return Optional.empty();
        }
    }

    @Override
    public PetResponseDto update(PetCreateRequestDto requestDto, Long id) {
        try {
            Optional<Pet> Pet = petDao.findById(id);
            if (Pet.isPresent())
            {
                Pet pet = petMapper.fromDto(requestDto);
                pet.setId(id);
                petDao.save(pet);
                log.info("Updating pet: {}", requestDto);
                return (petMapper.toPetResponse(pet));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update pet error:", e);
            return null;
        }
    }

    @Override
    public PetResponseDto updateWithImage(PetCreateRequestDto requestDto, Long id, MultipartFile image) {
        try {
            Optional<Pet> Pet = petDao.findById(id);
            if (Pet.isPresent())
            {
                Pet pet = petMapper.fromDto(requestDto);
                pet.setId(id);
                if (image!=null && !image.isEmpty())
                    pet.setImageAddress(imageStorage.saveImage(image));
                petDao.save(pet);
                log.info("Updating pet: {}", requestDto);
                return (petMapper.toPetResponse(pet));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update pet error:", e);
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            petDao.deleteById(id);
            log.info("Deleted pet: {}", id);
            return true;
        }
        catch (Exception e)
        {
            log.error("Deleting pet error:", e);
            return false;
        }
    }

    @Override
    public List<PetResponseDto> getAll() {
        try
        {
            List<Pet> pets = petDao.findAll();
            log.info("GetAll pets successfully:{}", pets);
            return (petMapper.toPetResponseList(pets));
        }
        catch (Exception e)
        {
            log.error("GetAll pet error:", e);
            return Collections.emptyList();
        }
    }
}

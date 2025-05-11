package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetRequest;

import ru.shelter.dto.response.PetResponse;

public interface PetInterface extends ServiceInterface<PetRequest, PetResponse, Long>{

    PetResponse add(PetRequest requestDto, MultipartFile image);

    PetResponse update(PetRequest requestDto, Long id, MultipartFile image);


}

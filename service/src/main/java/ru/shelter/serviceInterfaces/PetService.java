package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetCreateRequestDto;

import ru.shelter.dto.response.PetResponseDto;

public interface PetService extends ServiceInterface<PetCreateRequestDto, PetResponseDto, Long>{
    PetResponseDto addWithImage(PetCreateRequestDto requestDto, MultipartFile image);

    PetResponseDto updateWithImage(PetCreateRequestDto requestDto, Long id, MultipartFile image);
}

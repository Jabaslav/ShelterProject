package ru.shelter.serviceInterfaces;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetCreateRequestDto;

import ru.shelter.dto.response.PetResponseDto;

public interface PetService extends ServiceInterface<PetCreateRequestDto, PetResponseDto, Long>{
    @Transactional
    PetResponseDto add(PetCreateRequestDto requestDto);

    PetResponseDto addWithImage(PetCreateRequestDto requestDto, MultipartFile image);

    @Transactional
    PetResponseDto update(PetCreateRequestDto requestDto, Long id);

    PetResponseDto updateWithImage(PetCreateRequestDto requestDto, Long id, MultipartFile image);
}

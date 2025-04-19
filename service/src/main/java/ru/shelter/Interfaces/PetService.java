package ru.shelter.Interfaces;

import ru.shelter.dto.request.PetCreateRequestDto;

import ru.shelter.dto.response.PetResponseDto;

public interface PetService extends ServiceInterface<PetCreateRequestDto, PetResponseDto, Long>{
}

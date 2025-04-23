package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.PetCreateRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.model.Pet;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {
    PetResponseDto toPetResponse (Pet pet);

    List<PetResponseDto> toPetResponse (List<Pet> pet);

//    @Mapping(target="id", ignore=true)
//    @Mapping(target="imageAddress", ignore = true)
    Pet fromDto (PetCreateRequestDto requestDto);
}
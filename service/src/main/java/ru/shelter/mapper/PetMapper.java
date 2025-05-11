package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.PetRequest;
import ru.shelter.dto.response.PetResponse;
import ru.shelter.model.Pet;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {
    // Маппинг сущности в DTO
    PetResponse toPetResponse(Pet pet);

    // Маппинг списка сущностей
    List<PetResponse> toPetResponseList(List<Pet> pets);

    // Обновление сущности из DTO
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "imageAddress", ignore = true)
    void updateFromDto(PetRequest dto, @MappingTarget Pet pet);

    // Создание сущности из DTO
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "imageAddress", ignore = true)
    Pet fromDto(PetRequest requestDto);
}
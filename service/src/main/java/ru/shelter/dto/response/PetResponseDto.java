package ru.shelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetResponseDto {
    private final Long id;
    private final String name;
    private final String imageAddress;
    private final String type;
    private String description;
}

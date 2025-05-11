package ru.shelter.dto.response;

public record PetResponse(
    Long id,
    String name,
    String imageAddress,
    String type,
    String description
){}

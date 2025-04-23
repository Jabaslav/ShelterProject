package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// дто для создания питомца

public record PetCreateRequestDto (
        @NotEmpty
        @Size(max=30)
        String name,

        //@NotEmpty
        MultipartFile image,

        @NotEmpty
        @Size(max=30)
        String type,

        @NotEmpty
        @Size(max=140)
        String description
){ }

package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.shelter.validation.AtLeastOneNotNull;

// дто для создания поста

@AtLeastOneNotNull(fieldNames = {"description, image"})
public record PostCreateRequestDto (
        @NotEmpty
        Long authorId,

        @Size(max=140)
        String description

){}

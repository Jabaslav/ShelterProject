package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// дто для создания питомца
@Data
@AllArgsConstructor
public class PetCreateRequestDto {
    @NotEmpty
    @Size(max=30)
    private final String petName;

    //@NotEmpty
    private final MultipartFile image;

    @NotEmpty
    @Size(max=30)
    private final String type;

    @NotEmpty
    @Size(max=140)
    private final String description;
}

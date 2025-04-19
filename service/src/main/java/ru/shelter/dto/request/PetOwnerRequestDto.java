package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shelter.validation.AtLeastOneNotNull;

// Дто для запроса на добавление питомца к пользователю (установление владельца)
@Data
@AllArgsConstructor
public class PetOwnerRequestDto {

    @NotEmpty
    private final Long ownerId;

    @NotEmpty
    private final Long petId;
}

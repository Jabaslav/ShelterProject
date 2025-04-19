package ru.shelter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.shelter.validation.AtLeastOneNotNull;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AtLeastOneNotNull(fieldNames = {"userEmail, userPhoneNumber"})
public class UserCreateRequestDto {
    @NotEmpty
    private final String name;

    @NotEmpty
    private final LocalDate birthdayDate;

    @NotEmpty
    @Email
    private final String email;

    @NotEmpty
    //@Pattern(regexp()) Надо задать формат номера телефона
    private final String phoneNumber;

    @NotEmpty
    @Size(min = 8)
    private final String password;
};


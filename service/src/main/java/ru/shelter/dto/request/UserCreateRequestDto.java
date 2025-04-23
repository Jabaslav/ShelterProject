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


@AtLeastOneNotNull(fieldNames = {"userEmail, userPhoneNumber"})
public record UserCreateRequestDto (
    @NotEmpty
    String name,

    @NotEmpty
    LocalDate birthdayDate,

    @NotEmpty
    @Email
    String email,

    @NotEmpty
    //@Pattern(regexp()) Надо задать формат номера телефона
    String phoneNumber,

    @NotEmpty
    @Size(min = 8)
    String password
){};


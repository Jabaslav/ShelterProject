package ru.shelter.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import ru.shelter.validation.AtLeastOneNotNull;

import java.time.LocalDate;


@AtLeastOneNotNull(fieldNames = {"email", "phoneNumber"})
public record UserRequest(
    @NotEmpty(message = "Необходимо задать имя пользователя")
    String name,


    @NotNull (message = "Необходимо указать дату рождения")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdayDate,

    @NotNull(message= "Необходимо указать email")
    @Email
    String email,


    @Pattern(regexp = "^\\+[0-9]{10,15}$", message = "Номер телефона должен начинаться с + и содержать 10-15 символов")
    String phoneNumber,

    @NotEmpty(message = "Необходимо указать пароль")
    @Size(min = 8, message = "Минимальная длина пароля - 8 символов")
    String password
){};


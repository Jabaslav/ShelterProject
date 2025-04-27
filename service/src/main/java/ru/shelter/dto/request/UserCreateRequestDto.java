package ru.shelter.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;
import ru.shelter.validation.AtLeastOneNotNull;

import java.time.LocalDate;


@AtLeastOneNotNull(fieldNames = {"userEmail, userPhoneNumber"})
public record UserCreateRequestDto (
    //@NotEmpty
    String name,

    //@NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdayDate,

    //@NotEmpty
    //@Email
    String email,

    //@NotEmpty
    //@Pattern(regexp()) Надо задать формат номера телефона
    String phoneNumber,

    //@NotEmpty
    //@Size(min = 8)
    String password
){};


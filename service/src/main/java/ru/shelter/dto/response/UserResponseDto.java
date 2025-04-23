package ru.shelter.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public record UserResponseDto (

    String userName,

    LocalDate userBirthdayDate,

    String userEmail,


    //@Pattern(regexp()) Надо задать формат номера телефона
    String userPhoneNumber

){}

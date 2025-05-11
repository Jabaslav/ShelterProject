package ru.shelter.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record UserResponse(

    Long id,

    String name,

    LocalDate birthdayDate,

    String email,

    String phoneNumber,
    String profilePicAddress,

    LocalDateTime registerTime

){}

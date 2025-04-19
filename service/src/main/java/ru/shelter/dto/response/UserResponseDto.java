package ru.shelter.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    @NotEmpty
    private String userName;

    @NotEmpty
    private LocalDate userBirthdayDate;

    @NotEmpty
    @Email
    private String userEmail;

    @NotEmpty
    //@Pattern(regexp()) Надо задать формат номера телефона
    private String userPhoneNumber;


}

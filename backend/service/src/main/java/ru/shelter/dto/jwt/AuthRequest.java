package ru.shelter.dto.jwt;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

public record AuthRequest (
        @NotEmpty(message="Необходимо указать почту")
        @Email
        String email,

        @NotEmpty(message="Необходимо указать пароль")
        String password
){
}

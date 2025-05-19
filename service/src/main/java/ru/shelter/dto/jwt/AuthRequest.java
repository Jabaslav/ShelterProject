package ru.shelter.dto.jwt;


import jakarta.validation.constraints.NotEmpty;

public record AuthRequest (
        @NotEmpty (message="Необходимо указать имя пользователя")
        String name,
        @NotEmpty(message="Необходимо указать пароль")
        String password
){
}

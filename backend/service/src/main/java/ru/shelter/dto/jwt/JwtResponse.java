package ru.shelter.dto.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String type="Bearer";
    private String accessToken;
    private String refreshToken;

}

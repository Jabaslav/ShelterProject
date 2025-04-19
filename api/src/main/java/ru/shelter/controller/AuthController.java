package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shelter.dto.request.UserCreateRequestDto;


@RequestMapping("api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateRequestDto userDto)
    {
      return null;
    };

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserCreateRequestDto userDto)
    {
        return null;
    }
}

package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.jwt.AuthRequest;
import ru.shelter.dto.jwt.JwtAuthentication;
import ru.shelter.dto.jwt.JwtRefreshRequest;
import ru.shelter.dto.jwt.JwtResponse;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.impl.AuthService;

import java.util.Map;


@RequestMapping("api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/debug")
    public ResponseEntity<?> debugAuth(Authentication auth) {
        JwtAuthentication jwtAuth = (JwtAuthentication) auth.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "yourId", jwtAuth.getId(),
                "yourName", jwtAuth.getName(),
                "yourRole", jwtAuth.getRole()
        ));
    }

    @PostMapping(path = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JwtResponse> signup(
            @Valid @RequestPart(value = "userDto") UserRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image)
    {
        return new ResponseEntity<>(authService.register(request, image), HttpStatus.CREATED);
    };

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody AuthRequest authRequest) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/access")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRefreshRequest request) {
        final JwtResponse token = authService.getAccessToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRefreshRequest request) {
        final JwtResponse token = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(token);
    }
}

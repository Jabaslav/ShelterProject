package ru.shelter.impl;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.jwt.AuthRequest;
import ru.shelter.dto.jwt.JwtAuthentication;
import ru.shelter.dto.jwt.JwtResponse;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.exception.AuthenticationException;
import ru.shelter.exception.NotFoundException;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse register(UserRequest request, MultipartFile image) {
        User user = userService.add(request, image);
        return new JwtResponse(jwtService.generateAccessToken(user),jwtService.generateRefreshToken(user));
    }

    public void authenticateUser(AuthRequest authRequest){
        User user = userRepo.findByName(authRequest.name())
                .orElseThrow(()-> new AuthenticationException("Неправильный пароль или имя пользователя"));
        if (!passwordEncoder.matches(authRequest.password(), user.getPassword()))
            throw new AuthenticationException("Неправильный пароль или имя пользователя");
    }

    public JwtResponse login(@NonNull AuthRequest authRequest) {
        try {
            authenticateUser(authRequest);
            User user = userRepo.findByName(authRequest.name()).orElseThrow();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            refreshStorage.put(user.getName(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } catch (Error e){
            log.error("User signin error: {}", e.getMessage());
            throw e;
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        try
        {
            if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String name = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(name);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userRepo.findByName(name).orElseThrow();
                final String accessToken = jwtService.generateAccessToken(user);
                return new JwtResponse(accessToken, refreshToken);
            }
            } else throw new AuthenticationException("Invalid JWT");
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            throw e;

        }
        return null;
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String name = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(name);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userRepo.findByName(name).orElseThrow();
                final String accessToken = jwtService.generateAccessToken(user);
                final String newRefreshToken = jwtService.generateRefreshToken(user);
                refreshStorage.put(user.getName(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Invalid JWT!");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}

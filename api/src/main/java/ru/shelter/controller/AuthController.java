package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.impl.UserImpl;

import java.util.Optional;


@RequestMapping("api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserImpl userService;

    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @RequestPart("userDto") UserCreateRequestDto userDto,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) {
        try {
            if (image == null || image.isEmpty()) {
                return ResponseEntity.ok(userService.add(userDto));
            } else if (image.getContentType() != null && image.getContentType().startsWith("image/")) {
                return ResponseEntity.ok(userService.addWithImage(userDto, image));
            } else {
                return ResponseEntity.badRequest().body("Invalid file type");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логируем исключение
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    };

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserCreateRequestDto userDto)
    {
        return null;
    }
}

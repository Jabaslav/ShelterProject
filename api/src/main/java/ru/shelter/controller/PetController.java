package ru.shelter.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetCreateRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.impl.PetImpl;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetImpl petService;

    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@RequestBody PetCreateRequestDto requestDto) {
        try {
            PetResponseDto response = petService.add(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/with-image")
    public ResponseEntity<PetResponseDto> createPetWithImage(
            @RequestPart PetCreateRequestDto requestDto,
            @RequestPart MultipartFile image
    ) {
        try {
            PetResponseDto response = petService.addWithImage(requestDto, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPet(@PathVariable Long id) {
        Optional<PetResponseDto> response = petService.get(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDto> updatePet(
            @PathVariable Long id,
            @RequestBody PetCreateRequestDto requestDto
    ) {
        try {
            PetResponseDto response = petService.update(requestDto, id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/with-image")
    public ResponseEntity<PetResponseDto> updatePetWithImage(
            @PathVariable Long id,
            @RequestPart PetCreateRequestDto requestDto,
            @RequestPart MultipartFile image
    ) {
        try {
            PetResponseDto response = petService.updateWithImage(requestDto, id, image);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        boolean isDeleted = petService.remove(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getAllPets() {
        List<PetResponseDto> pets = petService.getAll();
        return ResponseEntity.ok(pets);
    }
}
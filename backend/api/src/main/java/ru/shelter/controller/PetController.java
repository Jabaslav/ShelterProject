package ru.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetRequest;
import ru.shelter.dto.response.PetResponse;
import ru.shelter.impl.PetService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPet(@RequestPart(value="petDto") PetRequest requestDto,
                                       @RequestPart(value="image", required = false) MultipartFile image) {
        return new ResponseEntity<>(petService.add(requestDto, image), HttpStatus.CREATED); // Http 201
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPet(@PathVariable Long id) {
        Optional<PetResponse> response = petService.get(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetResponse> updatePet(
            @PathVariable Long id,
            @RequestPart(value="petDto") PetRequest requestDto,
            @RequestPart(value="image", required = false) MultipartFile image
    ) {
            return new ResponseEntity<>(petService.update(requestDto, id, image), HttpStatus.OK); // Http 200
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        return new ResponseEntity<>(petService.remove(id), HttpStatus.NO_CONTENT); // Http 204
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets() {
        return new ResponseEntity<>(petService.getAll(), HttpStatus.OK); // Http 200
    }
}
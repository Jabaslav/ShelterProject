package ru.shelter.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.impl.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK); // Http 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK); // Http 200
    }

    @PreAuthorize("#userId=principal.id or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId)
    {
        userService.remove(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Http 204
    }

    @PreAuthorize("#userId=principal.getId")
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateUserData(@PathVariable("id") Long userId,
                                            @Valid @RequestPart(value="userDto") UserRequest request,
                                            @RequestPart(value="image", required = false) MultipartFile image)
    {
        return new ResponseEntity<>(userService.update(request, userId, image), HttpStatus.OK); // Http 200
    }



}

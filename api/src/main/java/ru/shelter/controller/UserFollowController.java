package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shelter.dto.request.UserFollowRequest;
import ru.shelter.impl.UserFollowService;

@RestController
@RequestMapping("api/users/{user_id}/follows")
@RequiredArgsConstructor
public class UserFollowController {
    private UserFollowService userFollowService;

    @PreAuthorize("#userId=principal.id")
    @PostMapping
    public ResponseEntity<?> addUserFollow(@PathVariable("user_id")Long  userId,
                                           @Valid @RequestBody UserFollowRequest request)
    {
        return new ResponseEntity<>(userFollowService.add(userId, request), HttpStatus.CREATED); // Http 201
    }
}

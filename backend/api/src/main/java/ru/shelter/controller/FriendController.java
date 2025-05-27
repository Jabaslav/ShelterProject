package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shelter.dto.request.FriendListRequest;
import ru.shelter.dto.response.FriendListResponse;
import ru.shelter.impl.FriendService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/{user_id}/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PreAuthorize("#userId == principal.getId()")
    @PostMapping
    public FriendListResponse addFriend(@PathVariable("user_id") Long userId,
                                        @Valid @RequestBody FriendListRequest requestDto) {
        return friendService.addFriend(userId, requestDto);
    }


    @GetMapping("/all")
    public List<FriendListResponse> getFriendsByUserId(@PathVariable("user_id") Long userId) {
        return friendService.getFriendsByUserId(userId);
    }

    @PreAuthorize("#userId == principal.id or hasRole('ADMIN')")
    @GetMapping("/requests")
    public List<FriendListResponse> getFriendRequestsByUserId(@PathVariable("user_id") Long userId) {
        return friendService.getFriendRequestsByUserId(userId);
    }

    @GetMapping("/{friendId}")
    public Optional<FriendListResponse> getFriendship(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId
    ) {
        return friendService.getFriendship(userId, friendId);
    }

    @PreAuthorize("#userId == principal.id")
    @PutMapping("/{friend_id}")
    public FriendListResponse updateFriendship(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId,
            @Valid @RequestBody FriendListRequest requestDto
    ) {
        return friendService.updateFriendship(userId, friendId, requestDto);
    }

    @PreAuthorize("principal.getId()=user_id")
    @DeleteMapping("/{friend_id}")
    public boolean deleteFriendship(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId
    ) {
        return friendService.deleteFriendship(userId, friendId);
    }
}


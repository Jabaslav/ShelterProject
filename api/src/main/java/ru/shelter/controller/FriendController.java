package ru.shelter.controller;

import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public FriendListResponse addFriend(@PathVariable("user_id") Long userId,
                                        @RequestBody FriendListRequest requestDto) {
        return friendService.addFriend(userId, requestDto);
    }

    @GetMapping("/all")
    public List<FriendListResponse> getFriendsByUserId(@PathVariable("user_id") Long userId) {
        return friendService.getFriendsByUserId(userId);
    }

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

    @PutMapping("/{friend_id}")
    public FriendListResponse updateFriendship(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId,
            @RequestBody FriendListRequest requestDto
    ) {
        return friendService.updateFriendship(userId, friendId, requestDto);
    }

    @DeleteMapping("/{friend_id}")
    public boolean deleteFriendship(
            @PathVariable("user_id") Long userId,
            @PathVariable("friend_id") Long friendId
    ) {
        return friendService.deleteFriendship(userId, friendId);
    }
}


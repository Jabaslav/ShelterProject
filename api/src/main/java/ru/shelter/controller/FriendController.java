package ru.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.impl.FriendImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendImpl friendService;

    @PostMapping
    public FriendListResponseDto addFriend(@RequestBody FriendListRequestDto requestDto) {
        return friendService.addFriend(requestDto);
    }

    @GetMapping("/{userId}")
    public List<FriendListResponseDto> getFriendsByUserId(@PathVariable Long userId) {
        return friendService.getFriendsByUserId(userId);
    }

    @GetMapping("/{userId}/{friendId}")
    public Optional<FriendListResponseDto> getFriendship(
            @PathVariable Long userId,
            @PathVariable Long friendId
    ) {
        return friendService.getFriendship(userId, friendId);
    }

    @PutMapping("/{userId}/{friendId}")
    public FriendListResponseDto updateFriendship(
            @PathVariable Long userId,
            @PathVariable Long friendId,
            @RequestBody FriendListRequestDto requestDto
    ) {
        return friendService.updateFriendship(userId, friendId, requestDto);
    }

    @DeleteMapping("/{userId}/{friendId}")
    public boolean deleteFriendship(
            @PathVariable Long userId,
            @PathVariable Long friendId
    ) {
        return friendService.deleteFriendship(userId, friendId);
    }
}


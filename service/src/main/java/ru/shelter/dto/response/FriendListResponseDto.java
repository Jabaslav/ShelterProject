package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shelter.model.FriendList;

import java.time.LocalDateTime;


public record FriendListResponseDto (

    Long userId,
    Long friendId,
    FriendList.FriendshipStatus status,
    LocalDateTime lastStatusChangeTime
){}

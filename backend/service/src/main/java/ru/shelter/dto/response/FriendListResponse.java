package ru.shelter.dto.response;

import ru.shelter.model.FriendList;

import java.time.LocalDateTime;


public record FriendListResponse(

    Long userId,
    Long friendId,
    FriendList.FriendshipStatus status,
    LocalDateTime lastStatusChangeTime
){}

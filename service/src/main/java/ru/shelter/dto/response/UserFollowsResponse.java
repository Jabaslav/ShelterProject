package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record UserFollowsResponse(
    Long followerId,
    Long followingId,
    LocalDateTime creationTime
){}

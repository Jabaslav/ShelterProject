package ru.shelter.dto.response;

import java.time.LocalDateTime;


public record UserFollowResponse(
    Long followerId,
    Long followingId,
    LocalDateTime creationTime
){}

package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


public record UserFollowsResponseDto (
    Long followerId,
    Long followingId,
    LocalDateTime creationTime
){}

package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserFollowsResponseDto {
    private final Long followerId;
    private final Long followingId;
    private final LocalDateTime creationTime;
}

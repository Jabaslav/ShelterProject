package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserFollowsRequestDto {
    // Удалим, будем брать из jwt
    @NotEmpty
    private final Long followerId;

    @NotEmpty
    private final Long followingId;

}

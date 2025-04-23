package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;


public record UserFollowsRequestDto (
    // Удалим, будем брать из jwt
    @NotEmpty
    Long followerId,

    @NotEmpty
    Long followingId

){}

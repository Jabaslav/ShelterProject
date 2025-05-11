package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;


public record UserFollowsRequest(
    // Удалим, будем брать из jwt
    @NotEmpty
    Long followerId,

    @NotEmpty
    Long followingId

){}

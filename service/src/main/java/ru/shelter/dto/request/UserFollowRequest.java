package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;


public record UserFollowRequest(
    // Удалим, будем брать из jwt

    @NotEmpty
    Long followingId

){}

package ru.shelter.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.shelter.model.FriendList;

public record FriendListRequest(

        @NotNull
        Long friendId,
        @NotNull
        FriendList.FriendshipStatus status
) {}
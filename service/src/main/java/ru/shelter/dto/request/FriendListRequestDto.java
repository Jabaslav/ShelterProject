package ru.shelter.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.shelter.model.FriendList;

public record FriendListRequestDto(
        @NotNull Long userId,
        @NotNull Long friendId,
        @NotNull FriendList.FriendshipStatus status
) {}
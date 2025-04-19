package ru.shelter.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shelter.model.FriendList;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class FriendListResponseDto {


    private final Long userId;
    private final Long friendId;
    private final FriendList.FriendshipStatus status;
    private LocalDateTime lastStatusChangeTime;
}

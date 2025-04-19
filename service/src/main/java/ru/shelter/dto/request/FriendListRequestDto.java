package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shelter.model.FriendList;

// Дто для отправления/принятия заявки в друзья, удаления друга
@Data
@RequiredArgsConstructor
public class FriendListRequestDto {

    //Тоже уберем, будем брать из jwt
    @NotEmpty
    private final Long userId;

    @NotEmpty
    private final Long friendId;

    @NotEmpty
    private final FriendList.FriendshipStatus status;
}

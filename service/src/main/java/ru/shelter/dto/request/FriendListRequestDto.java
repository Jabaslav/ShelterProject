package ru.shelter.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shelter.model.FriendList;

// Дто для отправления/принятия заявки в друзья, удаления друга


public record FriendListRequestDto (
        //Тоже уберем, будем брать из jwt
        @NotEmpty
        Long userId,

        @NotEmpty
        Long friendId,

        @NotEmpty
        FriendList.FriendshipStatus status
){ }

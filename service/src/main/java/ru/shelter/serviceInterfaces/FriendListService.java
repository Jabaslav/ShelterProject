package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.FriendListId;

import java.util.List;

public interface FriendListService extends ServiceInterface<FriendListRequestDto, FriendListResponseDto, FriendListId> {
    List<UserResponseDto> findFriendByUserId(Long userId);

    FriendListResponseDto add(FriendListRequestDto Object);

    FriendListResponseDto update(FriendListRequestDto Object, FriendListId friendListId);
}

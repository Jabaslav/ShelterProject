package ru.shelter.Interfaces;

import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.FriendListId;

import java.util.ArrayList;
import java.util.List;

public interface FriendListService extends ServiceInterface<FriendListRequestDto, FriendListResponseDto, FriendListId> {
    List<UserResponseDto> findFriendByUserId(Long userId);
}

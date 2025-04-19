package ru.shelter.Interfaces;

import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.FriendListId;

import java.util.ArrayList;

public interface FriendListService extends ServiceInterface<FriendListRequestDto, FriendListResponseDto, FriendListId> {
    ArrayList<UserResponseDto> findFriendByUserId(Long userId);
}

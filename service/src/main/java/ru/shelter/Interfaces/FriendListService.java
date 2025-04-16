package ru.shelter.Interfaces;

import ru.shelter.dto.FriendListDto;
import ru.shelter.dto.UserDto;
import ru.shelter.model.FriendListId;

import java.util.ArrayList;

public interface FriendListService extends ServiceInterface<FriendListDto, FriendListId> {
    ArrayList<UserDto> findFriendByUserId(Long userId);
}

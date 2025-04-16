package ru.shelter.Interfaces;

import ru.shelter.dto.UserDto;
import ru.shelter.dto.UserFollowsDto;
import ru.shelter.model.UserFollowsId;

import java.util.ArrayList;

public interface UserFollowsService extends ServiceInterface<UserFollowsDto, UserFollowsId>{
    ArrayList<UserDto> findFollowByUserId(Long userId);
    ArrayList<UserDto> findFollowerByUserId(Long userId);
}

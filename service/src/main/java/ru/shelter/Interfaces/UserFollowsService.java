package ru.shelter.Interfaces;

import ru.shelter.dto.request.UserFollowsRequestDto;
import ru.shelter.dto.response.UserFollowsResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.UserFollowsId;

import java.util.ArrayList;

public interface UserFollowsService extends ServiceInterface<UserFollowsRequestDto, UserFollowsResponseDto, UserFollowsId>{
    ArrayList<UserResponseDto> findFollowByUserId(Long userId);
    ArrayList<UserResponseDto> findFollowerByUserId(Long userId);
}

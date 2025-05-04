package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.UserFollowsRequestDto;
import ru.shelter.dto.response.UserFollowsResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.UserFollowsId;

import java.util.List;

public interface UserFollowsService extends ServiceInterface<UserFollowsRequestDto, UserFollowsResponseDto, UserFollowsId>{
    UserFollowsResponseDto add(UserFollowsRequestDto Object);

    UserFollowsResponseDto update(UserFollowsRequestDto Object, UserFollowsId userFollowsId);

    List<UserResponseDto> findFollowByUserId(Long userId);
    List<UserResponseDto> findFollowerByUserId(Long userId);
}

package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.UserFollowsRequest;
import ru.shelter.dto.response.UserFollowsResponse;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.model.UserFollowsId;

import java.util.List;

public interface UserFollowsInterface extends ServiceInterface<UserFollowsRequest, UserFollowsResponse, UserFollowsId>{
    UserFollowsResponse add(UserFollowsRequest Object);

    UserFollowsResponse update(UserFollowsRequest Object, UserFollowsId userFollowsId);

    List<UserResponse> findFollowByUserId(Long userId);
    List<UserResponse> findFollowerByUserId(Long userId);
}

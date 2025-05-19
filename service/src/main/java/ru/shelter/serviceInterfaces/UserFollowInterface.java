package ru.shelter.serviceInterfaces;

import ru.shelter.dto.request.UserFollowRequest;
import ru.shelter.dto.response.UserFollowResponse;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.model.UserFollowId;

import java.util.List;

public interface UserFollowInterface extends ServiceInterface<UserFollowRequest, UserFollowResponse, UserFollowId>{


    UserFollowResponse add(Long userId, UserFollowRequest request);

    List<UserFollowResponse> getAllFollows(Long userId);

    List<UserFollowResponse> getAllFollowers(Long userId);

    UserFollowResponse update(UserFollowRequest Object, UserFollowId userFollowId);

    List<UserResponse> findFollowByUserId(Long userId);
    List<UserResponse> findFollowerByUserId(Long userId);
}

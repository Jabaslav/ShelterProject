package ru.shelter.impl;

import ru.shelter.dto.response.UserResponse;
import ru.shelter.serviceInterfaces.UserFollowsInterface;
import ru.shelter.dto.request.UserFollowsRequest;
import ru.shelter.dto.response.UserFollowsResponse;
import ru.shelter.model.UserFollowsId;

import java.util.List;
import java.util.Optional;

public class UserFollowsService implements UserFollowsInterface {

    @Override
    public UserFollowsResponse add(UserFollowsRequest Object) {
        return null;
    }

    @Override
    public Optional<UserFollowsResponse> get(UserFollowsId userFollowsId) {
        return Optional.empty();
    }

    @Override
    public UserFollowsResponse update(UserFollowsRequest Object, UserFollowsId userFollowsId) {
        return null;
    }

    @Override
    public boolean remove(UserFollowsId userFollowsId) {
        return false;
    }

    @Override
    public List<UserFollowsResponse> getAll() {
        return null;
    }

    @Override
    public List<UserResponse> findFollowByUserId(Long userId) {
        return null;
    }

    @Override
    public List<UserResponse> findFollowerByUserId(Long userId) {
        return null;
    }
}

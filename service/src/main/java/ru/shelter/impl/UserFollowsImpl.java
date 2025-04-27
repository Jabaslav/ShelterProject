package ru.shelter.impl;

import ru.shelter.serviceInterfaces.UserFollowsService;
import ru.shelter.dto.request.UserFollowsRequestDto;
import ru.shelter.dto.response.UserFollowsResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.UserFollowsId;

import java.util.List;
import java.util.Optional;

public class UserFollowsImpl implements UserFollowsService {

    @Override
    public UserFollowsResponseDto add(UserFollowsRequestDto Object) {
        return null;
    }

    @Override
    public Optional<UserFollowsResponseDto> get(UserFollowsId userFollowsId) {
        return Optional.empty();
    }

    @Override
    public UserFollowsResponseDto update(UserFollowsRequestDto Object, UserFollowsId userFollowsId) {
        return null;
    }

    @Override
    public boolean remove(UserFollowsId userFollowsId) {
        return false;
    }

    @Override
    public List<UserFollowsResponseDto> getAll() {
        return null;
    }

    @Override
    public List<UserResponseDto> findFollowByUserId(Long userId) {
        return null;
    }

    @Override
    public List<UserResponseDto> findFollowerByUserId(Long userId) {
        return null;
    }
}

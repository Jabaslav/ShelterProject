package ru.shelter.impl;

import ru.shelter.Interfaces.UserFollowsService;
import ru.shelter.dto.request.UserFollowsRequestDto;
import ru.shelter.dto.response.UserFollowsResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.UserFollowsId;

import java.util.ArrayList;
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
    public boolean update(UserFollowsRequestDto Object, UserFollowsId userFollowsId) {
        return false;
    }

    @Override
    public boolean remove(UserFollowsId userFollowsId) {
        return false;
    }

    @Override
    public ArrayList<UserFollowsResponseDto> getAll() {
        return null;
    }

    @Override
    public ArrayList<UserResponseDto> findFollowByUserId(Long userId) {
        return null;
    }

    @Override
    public ArrayList<UserResponseDto> findFollowerByUserId(Long userId) {
        return null;
    }
}

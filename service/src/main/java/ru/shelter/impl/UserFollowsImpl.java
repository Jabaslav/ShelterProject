package ru.shelter.impl;

import ru.shelter.Interfaces.UserFollowsService;
import ru.shelter.dto.UserDto;
import ru.shelter.dto.UserFollowsDto;
import ru.shelter.model.UserFollowsId;

import java.util.ArrayList;
import java.util.Optional;

public class UserFollowsImpl implements UserFollowsService {
    @Override
    public UserFollowsDto add(UserFollowsDto object) {
        return null;
    }

    @Override
    public Optional<UserFollowsDto> get(UserFollowsId userFollowsId) {
        return Optional.empty();
    }

    @Override
    public boolean update(UserFollowsDto Object, UserFollowsId userFollowsId) {
        return false;
    }

    @Override
    public boolean remove(UserFollowsId userFollowsId) {
        return false;
    }

    @Override
    public ArrayList<UserFollowsDto> getAll() {
        return null;
    }

    @Override
    public ArrayList<UserDto> findFollowByUserId(Long userId) {
        return null;
    }

    @Override
    public ArrayList<UserDto> findFollowerByUserId(Long userId) {
        return null;
    }
}

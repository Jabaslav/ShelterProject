package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.FriendListService;
import ru.shelter.dto.FriendListDto;
import ru.shelter.dto.UserDto;
import ru.shelter.model.FriendListId;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FriendListImpl implements FriendListService {
    @Override
    public ArrayList<UserDto> findFriendByUserId(Long userId) {
        return null;
    }

    @Override
    public FriendListDto add(FriendListDto object) {
        return null;
    }

    @Override
    public Optional<FriendListDto> get(FriendListId friendListId) {
        return Optional.empty();
    }

    @Override
    public boolean update(FriendListDto Object, FriendListId friendListId) {
        return false;
    }

    @Override
    public boolean remove(FriendListId friendListId) {
        return false;
    }

    @Override
    public ArrayList<FriendListDto> getAll() {
        return null;
    }
}

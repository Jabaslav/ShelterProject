package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.FriendListService;

import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.model.FriendListId;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FriendListImpl implements FriendListService {

    @Override
    public ArrayList<UserResponseDto> findFriendByUserId(Long userId) {
        return null;
    }

    @Override
    public FriendListResponseDto add(FriendListRequestDto Object) {
        return null;
    }

    @Override
    public Optional<FriendListResponseDto> get(FriendListId friendListId) {
        return Optional.empty();
    }

    @Override
    public FriendListResponseDto update(FriendListRequestDto Object, FriendListId friendListId) {
        return null;
    }

    @Override
    public boolean remove(FriendListId friendListId) {
        return false;
    }

    @Override
    public ArrayList<FriendListResponseDto> getAll() {
        return null;
    }
}

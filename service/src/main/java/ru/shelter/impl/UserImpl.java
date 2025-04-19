package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.UserService;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;


import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserImpl implements UserService {


    @Override
    public UserResponseDto add(UserCreateRequestDto Object) {
        return null;
    }

    @Override
    public Optional<UserResponseDto> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean update(UserCreateRequestDto Object, Long aLong) {
        return false;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public ArrayList<UserResponseDto> getAll() {
        return null;
    }
}

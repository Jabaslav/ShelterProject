package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.UserService;
import ru.shelter.dto.UserDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserImpl implements UserService {
    @Override
    public UserDto add(UserDto Object) {
        return null;
    }

    @Override
    public Optional<UserDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(UserDto Object, Long id) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public ArrayList<UserDto> getAll() {
        return null;
    }
}

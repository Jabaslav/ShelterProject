package ru.shelter.Interfaces;

import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;

public interface UserService extends ServiceInterface<UserCreateRequestDto, UserResponseDto, Long>{
}

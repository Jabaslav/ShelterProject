package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;

public interface UserService extends ServiceInterface<UserCreateRequestDto, UserResponseDto, Long>{

    UserResponseDto add(UserCreateRequestDto requestDto, MultipartFile image);

    UserResponseDto update(UserCreateRequestDto requestDto, Long id, MultipartFile image);
}

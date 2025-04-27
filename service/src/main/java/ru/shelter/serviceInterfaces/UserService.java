package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;

public interface UserService extends ServiceInterface<UserCreateRequestDto, UserResponseDto, Long>{
    UserResponseDto addWithImage(UserCreateRequestDto requestDto, MultipartFile image);

    UserResponseDto updateWithImage(UserCreateRequestDto requestDto, Long id, MultipartFile image);
}

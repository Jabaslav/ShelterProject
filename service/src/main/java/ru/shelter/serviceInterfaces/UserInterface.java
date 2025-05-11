package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.dto.response.UserResponse;

public interface UserInterface extends ServiceInterface<UserRequest, UserResponse, Long>{

    UserResponse add(UserRequest requestDto, MultipartFile image);

    UserResponse update(UserRequest requestDto, Long id, MultipartFile image);
}

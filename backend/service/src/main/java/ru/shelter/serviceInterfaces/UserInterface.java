package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.model.User;

public interface UserInterface extends ServiceInterface<UserRequest, UserResponse, Long>{

    User add(UserRequest requestDto, MultipartFile image);

    UserResponse update(UserRequest requestDto, Long id, MultipartFile image);
}

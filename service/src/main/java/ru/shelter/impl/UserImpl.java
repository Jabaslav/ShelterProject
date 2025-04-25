package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.Interfaces.UserService;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageStorageImpl imageStorage;


    @Override
    public UserResponseDto add(UserCreateRequestDto requestDto) {
        try {
            User user = userMapper.fromDto(requestDto); // создание entity через маппер, пароль не заполняется
            user.setPassword(passwordEncoder.encode(requestDto.password()));// Хэширование пароля
            userDao.save(user); // сохраняем пользователя в бд
            UserResponseDto response = userMapper.toUserResponse(user);
            log.info("Adding user: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add user error:", e);
        };
        return null;
    }

    @Override
    public UserResponseDto addWithImage(UserCreateRequestDto requestDto, MultipartFile image) {
        try {
            User user = userMapper.fromDto(requestDto); // создание entity через маппер, пароль не заполняется
            user.setPassword(passwordEncoder.encode(requestDto.password()));// Хэширование пароля
            if (image !=null && !image.isEmpty())
            {
                user.setProfilePicAddress(imageStorage.saveImage(image));
            }
            userDao.save(user); // сохраняем пользователя в бд
            UserResponseDto response = userMapper.toUserResponse(user);
            log.info("Adding user: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add user error:", e);
        };
        return null;
    }



    @Override
    public Optional<UserResponseDto> get(Long id) {
        try {
            Optional<UserResponseDto> response = userDao.findById(id).map(userMapper::toUserResponse);
            if (response.isPresent())
                log.info("Get user:", response);
            else
                log.info("User with id", id, "not found");
            return response;
        }
        catch (Exception e)
        {
            log.error ("Get user error:", e);
            return Optional.empty();
        }
    }

    @Override
    public UserResponseDto update(UserCreateRequestDto requestDto, Long id) {
        try {
            Optional<User> user = userDao.findById(id);
            if (user.isPresent())
            {
                User updatedUser = userMapper.fromDto(requestDto);
                updatedUser.setId(id);
                userDao.save(updatedUser);
                log.info("Updating user: {}", requestDto);
                return (userMapper.toUserResponse(updatedUser));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update user error:", e);
            return null;
        }
    }

    @Override
    public UserResponseDto updateWithImage(UserCreateRequestDto requestDto, Long id, MultipartFile image) {
        try {
            Optional<User> user = userDao.findById(id);
            if (user.isPresent())
            {
                User updatedUser = userMapper.fromDto(requestDto);
                updatedUser.setId(id);
                if (image !=null && !image.isEmpty())
                {
                    updatedUser.setProfilePicAddress(imageStorage.saveImage(image));
                }
                userDao.save(updatedUser);
                log.info("Updating user: {}", requestDto);
                return (userMapper.toUserResponse(updatedUser));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update user error:", e);
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
                userDao.deleteById(id);
                log.info("Deleted user: {}", id);
                return true;
        }
        catch (Exception e)
        {
            log.error("Deleting user error:", e);
            return false;
        }
    }

    @Override
    public List<UserResponseDto> getAll() {
        try
        {
            List<User> users = userDao.findAll();
            log.info("GetAll users successfully:{}", users);
            return (userMapper.toUserResponseList(users));
        }
        catch (Exception e)
        {
            log.error("GetAll user error:", e);
            return Collections.emptyList();
        }
    }
}

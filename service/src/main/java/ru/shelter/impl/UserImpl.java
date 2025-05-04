package ru.shelter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.exception.ImageFileException;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;
import ru.shelter.serviceInterfaces.UserService;

import java.util.List;
import java.util.NoSuchElementException;
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
    public UserResponseDto add(UserCreateRequestDto requestDto, MultipartFile image) {
        try {
            if (image == null || image.isEmpty() || !image.getContentType().startsWith("image/")) {
                throw new ImageFileException("Не удалось считать изображение из файла");
            }
            User user = userMapper.fromDto(requestDto); // создание entity через маппер, пароль не заполняется
            user.setPassword(passwordEncoder.encode(requestDto.password()));// Хэширование пароля
            user.setProfilePicAddress(imageStorage.saveImage(image));
            userDao.save(user); // сохраняем пользователя в бд
            UserResponseDto response = userMapper.toUserResponse(user);
            log.info("Adding user: {}", response);
            return response;
        }
        catch (DataAccessException e)
        {
            log.error("Database error:", e);
            throw e;
        }
        catch (Exception e)
        {
            log.error("Add user error:", e);
            throw e;
        }
    }

    @Override
    public Optional<UserResponseDto> get(Long id) {
        try {
            return Optional.ofNullable(userMapper.toUserResponse
                    (userDao.findById(id).orElseThrow()));
        } catch (NoSuchElementException e){
            log.error("No user with id {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Get user error:", e);
            throw e;
        }
    }

    @Override
    public UserResponseDto update(UserCreateRequestDto requestDto, Long id, MultipartFile image) {
        try {
            userDao.findById(id).orElseThrow();
            if (image == null || image.isEmpty() || !image.getContentType().startsWith("image/")) {
                throw new ImageFileException("Не удалось считать изображение из файла");
            }
                User updatedUser = userMapper.fromDto(requestDto);
                updatedUser.setId(id);
                userDao.save(updatedUser);
                log.info("Updating user: {}", requestDto);
                return (userMapper.toUserResponse(updatedUser));
        }
        catch (Exception e)
        {
            log.error("Update user error:", e);
            throw e;
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
            throw e;
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
            throw e;
        }
    }
}

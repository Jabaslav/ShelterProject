package ru.shelter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserRequest;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.exception.NotFoundException;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;
import ru.shelter.serviceInterfaces.UserInterface;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserInterface {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageStorageService imageStorage;


    @Override
    public UserResponse add(UserRequest requestDto, MultipartFile image) {
        try {
            User user = userMapper.fromDto(requestDto); // создание entity через маппер, пароль не заполняется
            user.setPassword(passwordEncoder.encode(requestDto.password()));// Хэширование пароля
            if (imageStorage.validateImage(image)){
                user.setProfilePicAddress(imageStorage.saveImage(image));
            }
            userRepo.save(user); // сохраняем пользователя в бд
            UserResponse response = userMapper.toUserResponse(user);
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
    public Optional<UserResponse> get(Long id) {
        try {
            return Optional.ofNullable(userMapper.toUserResponse
                    (userRepo.findById(id).orElseThrow()));
        } catch (NoSuchElementException e){
            log.error("No user with id {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Get user error:", e);
            throw e;
        }
    }

    @Override
    public UserResponse update(UserRequest requestDto, Long id, MultipartFile image) {
        try {
            userRepo.findById(id).orElseThrow();
            User updatedUser = userMapper.fromDto(requestDto);
            if (imageStorage.validateImage(image)){
                updatedUser.setProfilePicAddress(imageStorage.saveImage(image));
            }
            updatedUser.setId(id);
            userRepo.save(updatedUser);
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
            if (userRepo.existsById(id))
            {
                userRepo.deleteById(id);
                log.info("Deleted user: {}", id);
                return true;
            }
            else throw new NotFoundException("User with id " + id + " not found");
        }
        catch (Exception e)
        {
            log.error("Deleting user error:", e);
            throw e;
        }
    }

    @Override
    public List<UserResponse> getAll() {
        try
        {
            List<User> users = userRepo.findAll();
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

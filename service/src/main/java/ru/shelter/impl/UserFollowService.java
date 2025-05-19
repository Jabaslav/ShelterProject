package ru.shelter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shelter.dto.response.UserResponse;
import ru.shelter.exception.NotFoundException;
import ru.shelter.exception.ValidationException;
import ru.shelter.interfaces.UserFollowRepo;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.UserFollowMapper;
import ru.shelter.model.UserFollow;
import ru.shelter.serviceInterfaces.UserFollowInterface;
import ru.shelter.dto.request.UserFollowRequest;
import ru.shelter.dto.response.UserFollowResponse;
import ru.shelter.model.UserFollowId;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Slf4j
public class UserFollowService implements UserFollowInterface {
    private UserFollowRepo userFollowRepo;
    private UserFollowMapper userFollowMapper;
    private UserRepo userRepo;

    @Override
    public UserFollowResponse add(Long userId, UserFollowRequest request) {
        try{
            userRepo.findById(userId).orElseThrow(()->new ValidationException("Пользователь, который хочет оформить подписку, не существует"));
            userRepo.findById(request.followingId()).orElseThrow(()->new ValidationException("Пользователь, на которого вы пытаетесь оформить подписку, не существует"));
            UserFollow userFollow = userFollowMapper.fromDto(request);
            userFollow.setFollowerId(userId);
            UserFollowResponse response = userFollowMapper.toUserFollowResponse(userFollowRepo.save(userFollow));
            log.info("Added follow: user {} now follows user {}", userId, request.followingId() );
            return response;
        } catch (Exception e)
        {
            log.error("Error adding userFollow {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserFollowResponse> getAllFollows(Long userId) {
        try{
            userRepo.findById(userId).orElseThrow(()->new NotFoundException("Пользователя с таким id не существует"));
            List<UserFollowResponse> response = userFollowMapper.toUserFollowResponseList(userFollowRepo.findAllFollows(userId));
            log.info("Found all follows for user {}", userId);
            return response;
        } catch (Exception e)
        {
            log.error("Error finding user follows {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserFollowResponse> getAllFollowers(Long userId) {
        try{
            userRepo.findById(userId).orElseThrow(()->new NotFoundException("Пользователя с таким id не существует"));
            List<UserFollowResponse> response = userFollowMapper.toUserFollowResponseList(userFollowRepo.findAllFollowers(userId));
            log.info("Found all followers for user {}", userId);
            return response;
        } catch (Exception e)
        {
            log.error("Error finding user followers {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<UserFollowResponse> get(UserFollowId userFollowId) {
        return Optional.empty();
    }

    @Override
    public UserFollowResponse update(UserFollowRequest Object, UserFollowId userFollowId) {
        return null;
    }

    @Override
    public boolean remove(UserFollowId userFollowId) {
        return false;
    }

    @Override
    public List<UserFollowResponse> getAll() {
        return null;
    }

    @Override
    public List<UserResponse> findFollowByUserId(Long userId) {
        return null;
    }

    @Override
    public List<UserResponse> findFollowerByUserId(Long userId) {
        return null;
    }
}

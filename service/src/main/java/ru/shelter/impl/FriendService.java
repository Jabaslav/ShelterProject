package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shelter.dto.request.FriendListRequest;
import ru.shelter.dto.response.FriendListResponse;
import ru.shelter.interfaces.FriendListRepo;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.FriendListMapper;
import ru.shelter.model.FriendList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendListRepo friendListRepository;
    private final FriendListMapper friendMapper;
    private final UserRepo userRepo;

    public FriendListResponse addFriend(Long userId, FriendListRequest requestDto) {
        try {
            if (Objects.equals(userId, requestDto.friendId()))
                throw new Exception("User can't add himself to friendlist");
            if (!userRepo.existsById(requestDto.friendId()))
                throw new EntityNotFoundException("No user found with id {}" + requestDto.friendId());
            FriendList friendList = friendMapper.fromDto(requestDto);
            friendList.setUser(userRepo.findById(userId).orElseThrow(()->new EntityNotFoundException("No user found with id {}" + userId)));
            FriendList savedFriendList = friendListRepository.save(friendList);
            log.info("Added friend: {}", friendList);
            return friendMapper.toDto(savedFriendList);
        } catch (Exception e) {
            log.error("Error adding friend:", e);
            throw new RuntimeException("Failed to add friend");
        }
    }

    public List<FriendListResponse> getFriendsByUserId(Long userId) {
        try {
            List<FriendList> friends = friendListRepository.findByUserId(userId);
            return friends.stream()
                    .map(friendMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching friends for user ID {}:", userId, e);
            throw new RuntimeException("Failed to fetch friends");
        }
    }

    public List<FriendListResponse> getFriendRequestsByUserId(Long userId) {
        try {
            List<FriendList> friends = friendListRepository.findRequestsByUserId(userId);
            return friends.stream()
                    .map(friendMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching friends for user ID {}:", userId, e);
            throw new RuntimeException("Failed to fetch friends");
        }
    }

    public Optional<FriendListResponse> getFriendship(Long userId, Long friendId) {
        try {
            return friendListRepository.findByUserIdAndFriendId(userId, friendId)
                    .map(friendMapper::toDto);
        } catch (Exception e) {
            log.error("Error fetching friendship between user ID {} and friend ID {}:", userId, friendId, e);
            throw e;
        }
    }

    public FriendListResponse updateFriendship(Long userId, Long friendId, FriendListRequest requestDto) {
        try {
            FriendList friendList = friendListRepository.findByUserIdAndFriendId(userId, friendId)
                    .orElseThrow(EntityNotFoundException::new);
            friendMapper.updateFromDto(requestDto, friendList);
            FriendList savedFriendList = friendListRepository.save(friendList);
            log.info("Updated friendship: {}", friendList);
            return friendMapper.toDto(savedFriendList);
        } catch (Exception e) {
            log.error("Error updating friendship between user ID {} and friend ID {}:", userId, friendId, e);
            throw new RuntimeException("Failed to update friendship");
        }
    }

    public boolean deleteFriendship(Long userId, Long friendId) {
        try {
            friendListRepository.deleteByUserIdAndFriendId(userId, friendId);
            log.info("Deleted friendship between user ID {} and friend ID {}", userId, friendId);
            return true;
        } catch (Exception e) {
            log.error("Error deleting friendship between user ID {} and friend ID {}:", userId, friendId, e);
            return false;
        }
    }
}

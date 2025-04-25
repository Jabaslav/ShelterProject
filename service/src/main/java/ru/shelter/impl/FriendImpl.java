package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shelter.dto.request.FriendListRequestDto;
import ru.shelter.dto.response.FriendListResponseDto;
import ru.shelter.interfaces.FriendListDao;
import ru.shelter.mapper.FriendListMapper;
import ru.shelter.model.FriendList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FriendImpl {

    private final FriendListDao friendListRepository;
    private final FriendListMapper friendMapper;

    public FriendListResponseDto addFriend(FriendListRequestDto requestDto) {
        try {
            FriendList friendList = friendMapper.fromDto(requestDto);
            friendListRepository.save(friendList);
            log.info("Added friend: {}", friendList);
            return friendMapper.toDto(friendList);
        } catch (Exception e) {
            log.error("Error adding friend:", e);
            throw new RuntimeException("Failed to add friend");
        }
    }

    public List<FriendListResponseDto> getFriendsByUserId(Long userId) {
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

    public Optional<FriendListResponseDto> getFriendship(Long userId, Long friendId) {
        try {
            return friendListRepository.findByUserIdAndFriendId(userId, friendId)
                    .map(friendMapper::toDto);
        } catch (Exception e) {
            log.error("Error fetching friendship between user ID {} and friend ID {}:", userId, friendId, e);
            return Optional.empty();
        }
    }

    public FriendListResponseDto updateFriendship(Long userId, Long friendId, FriendListRequestDto requestDto) {
        try {
            FriendList friendList = friendListRepository.findByUserIdAndFriendId(userId, friendId)
                    .orElseThrow(EntityNotFoundException::new);
            friendMapper.updateFromDto(requestDto, friendList);
            friendListRepository.save(friendList);
            log.info("Updated friendship: {}", friendList);
            return friendMapper.toDto(friendList);
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

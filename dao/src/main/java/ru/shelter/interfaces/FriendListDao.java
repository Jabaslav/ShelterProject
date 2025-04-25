package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.FriendList;
import ru.shelter.model.FriendListId;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendListDao extends JpaRepository<FriendList, FriendListId> {
    Optional<FriendList> findByUserIdAndFriendId(Long userId, Long friendId);
    void deleteByUserIdAndFriendId(Long userId, Long friendId);
    List<FriendList> findByUserId(Long userId);
}
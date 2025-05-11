package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shelter.model.FriendList;
import ru.shelter.model.FriendListId;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendListRepo extends JpaRepository<FriendList, FriendListId> {
    Optional<FriendList> findByUserIdAndFriendId(Long userId, Long friendId);

    void deleteByUserIdAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query("SELECT fl FROM FriendList fl WHERE fl.userId = :userId AND fl.status=ACCEPTED")
    List<FriendList> findByUserId(@Param("userId") Long userId);

    @Query("SELECT fl FROM FriendList fl WHERE fl.userId = :userId AND fl.status!=ACCEPTED")
    List<FriendList> findRequestsByUserId(@Param("userId") Long userId);
}
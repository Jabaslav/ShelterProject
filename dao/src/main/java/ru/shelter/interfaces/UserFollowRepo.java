package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shelter.model.UserFollow;
import ru.shelter.model.UserFollowId;

import java.util.List;

@Repository
public interface UserFollowRepo extends JpaRepository<UserFollow, UserFollowId> {
    @Query("SELECT uf FROM UserFollow uf WHERE uf.followerId=:userId")
    public List<UserFollow> findAllFollows(@Param("userId") Long userId);

    @Query("SELECT uf FROM UserFollow uf WHERE uf.followingId=:userId")
    public List<UserFollow> findAllFollowers(@Param("userId") Long userId);
}

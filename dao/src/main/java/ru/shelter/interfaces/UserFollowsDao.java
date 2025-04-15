package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.UserFollows;

@Repository
public interface UserFollowsDao extends JpaRepository<UserFollows, UserFollows.UserFollowsId> {
}

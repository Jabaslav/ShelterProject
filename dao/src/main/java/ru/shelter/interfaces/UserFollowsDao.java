package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.UserFollows;
import ru.shelter.model.UserFollowsId;

@Repository
public interface UserFollowsDao extends JpaRepository<UserFollows, UserFollowsId> {
}

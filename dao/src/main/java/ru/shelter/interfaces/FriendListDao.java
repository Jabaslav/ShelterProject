package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.FriendList;

@Repository
public interface FriendListDao extends JpaRepository<FriendList, Long> {
}


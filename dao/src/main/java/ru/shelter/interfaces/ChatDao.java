package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.Chat;

@Repository
public interface ChatDao extends JpaRepository<Chat, Long> {
}

package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.Message;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
}

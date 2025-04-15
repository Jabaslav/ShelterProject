package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shelter.model.ChatParticipant;

@Repository
public interface ChatParticipantDao extends JpaRepository<ChatParticipant, ChatParticipant.ChatParticipantId> {
}

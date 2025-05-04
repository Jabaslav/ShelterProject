package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shelter.model.ChatParticipant;
import ru.shelter.model.ChatParticipantId;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantDao extends JpaRepository<ChatParticipant, ChatParticipantId> {
    @Query("SELECT * FROM chat_participants WHERE chat_id=:chatId")
    Optional<List<ChatParticipant>> findUsersByChatId(@Param("chatId") Long chatId);

    @Query("SELECT chat_id FROM chat_participants WHERE participant_id=:userId")
    Optional<List<ChatParticipant>> findChatsByUserId(@Param("userId") Long chatId);
}

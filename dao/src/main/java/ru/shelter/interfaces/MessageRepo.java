package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shelter.model.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.chatId = :chatId ORDER BY m.creationTime DESC")
    List<Message> findAllByChatId(@Param("chatId") Long chatId);

    @Query("SELECT m FROM Message m WHERE m.authorId = :userId ORDER BY m.creationTime DESC")
    List<Message> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE m.chatId = :chatId AND m.authorId = :userId ORDER BY m.creationTime DESC")
    List<Message> findAllByChatIdAndUserId(@Param("chatId") Long chatId, @Param("userId") Long userId);
}

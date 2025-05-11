package ru.shelter.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shelter.model.ChatMember;
import ru.shelter.model.ChatMemberId;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMemberRepo extends JpaRepository<ChatMember, ChatMemberId> {
    @Query("SELECT cm FROM ChatMember cm WHERE cm.chatId=chatId")
    Optional<List<ChatMember>> findUsersByChatId(@Param("chatId") Long chatId);

    @Query("SELECT cm FROM ChatMember cm WHERE cm.userId=userId")
    Optional<List<ChatMember>> findChatsByUserId(@Param("userId") Long chatId);
}

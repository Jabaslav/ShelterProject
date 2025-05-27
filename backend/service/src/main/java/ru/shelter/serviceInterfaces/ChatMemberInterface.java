package ru.shelter.serviceInterfaces;


import ru.shelter.dto.request.ChatMemberRequest;
import ru.shelter.dto.response.ChatMemberResponse;
import ru.shelter.model.ChatMemberId;

import java.util.List;

public interface ChatMemberInterface extends ServiceInterface<ChatMemberRequest, ChatMemberResponse, ChatMemberId> {
    List<ChatMemberResponse> findUsersByChatId(Long chatId);
    List<ChatMemberResponse> findChatsByUserId(Long userId);

    ChatMemberResponse add(ChatMemberRequest requestDto);

    ChatMemberResponse update(ChatMemberRequest requestDto);

    boolean isUserInChat(Long userId, Long chatId);
}

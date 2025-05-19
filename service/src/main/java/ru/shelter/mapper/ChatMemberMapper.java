package ru.shelter.mapper;

import org.mapstruct.Mapper;
import ru.shelter.dto.request.ChatMemberRequest;
import ru.shelter.dto.response.ChatMemberResponse;
import ru.shelter.model.ChatMember;

import java.util.List;

@Mapper(componentModel="spring")
public interface ChatMemberMapper {
    ChatMemberResponse toChatParticipantResponse(ChatMember chatMember);
    List<ChatMemberResponse> toChatParticipantResponseList(List<ChatMember> chatMemberList);
    ChatMember fromDto(ChatMemberRequest requestDto);
}
package ru.shelter.mapper;

import org.mapstruct.Mapper;
import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatParticipantResponseDto;
import ru.shelter.model.ChatParticipant;

import java.util.List;

@Mapper(componentModel="spring")
public interface ChatParticipantMapper {
    ChatParticipantResponseDto toChatParticipantResponse(ChatParticipant chatParticipant);
    List<ChatParticipantResponseDto> toChatParticipantResponseList(List<ChatParticipant> chatParticipantList);
    ChatParticipant fromDto(ChatParticipantRequestDto requestDto);
}
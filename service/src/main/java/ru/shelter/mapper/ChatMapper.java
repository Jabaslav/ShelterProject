package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.response.ChatResponseDto;
import ru.shelter.model.Chat;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    ChatResponseDto toChatResponse (Chat chat);

    List<ChatResponseDto> toChatResponseList (List<Chat> chats);

    Chat fromDto (ChatCreateRequestDto requestDto);
}
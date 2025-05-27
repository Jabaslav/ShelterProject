package ru.shelter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.shelter.dto.request.ChatRequest;
import ru.shelter.dto.response.ChatResponse;
import ru.shelter.model.Chat;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    ChatResponse toChatResponse (Chat chat);

    List<ChatResponse> toChatResponseList (List<Chat> chats);

    Chat fromDto (ChatRequest requestDto);
}
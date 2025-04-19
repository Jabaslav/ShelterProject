package ru.shelter.Interfaces;

import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.response.ChatResponseDto;

public interface ChatService extends ServiceInterface<ChatCreateRequestDto, ChatResponseDto, Long>{
}

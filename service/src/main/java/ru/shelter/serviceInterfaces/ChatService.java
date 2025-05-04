package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.response.ChatResponseDto;

public interface ChatService extends ServiceInterface<ChatCreateRequestDto, ChatResponseDto, Long>{
    ChatResponseDto add(ChatCreateRequestDto requestDto, MultipartFile image);

    ChatResponseDto update(ChatCreateRequestDto requestDto, Long id);

    ChatResponseDto updateWithImage(ChatCreateRequestDto requestDto, Long id, MultipartFile multipartFile);
}

package ru.shelter.serviceInterfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatRequest;
import ru.shelter.dto.response.ChatResponse;

public interface ChatInterface extends ServiceInterface<ChatRequest, ChatResponse, Long>{
    ChatResponse add(ChatRequest requestDto, MultipartFile image);

    ChatResponse update(ChatRequest requestDto, Long id, MultipartFile multipartFile);
}

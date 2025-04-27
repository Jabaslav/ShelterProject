package ru.shelter.serviceInterfaces;


import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageCreateRequestDto;
import ru.shelter.dto.response.MessageResponseDto;


public interface MessageService extends ServiceInterface<MessageCreateRequestDto, MessageResponseDto, Long>{
    MessageResponseDto addWithImage(MessageCreateRequestDto requestDto, MultipartFile image);

    MessageResponseDto updateWithImage(MessageCreateRequestDto requestDto, Long id, MultipartFile image);
}

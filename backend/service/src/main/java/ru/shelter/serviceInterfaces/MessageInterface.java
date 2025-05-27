package ru.shelter.serviceInterfaces;


import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageRequest;
import ru.shelter.dto.response.MessageResponse;


public interface MessageInterface extends ServiceInterface<MessageRequest, MessageResponse, Long>{


    MessageResponse add(Long chatId, MessageRequest requestDto, MultipartFile image);

    MessageResponse update(MessageRequest requestDto, Long id, MultipartFile image);
}

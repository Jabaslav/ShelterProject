package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageRequest;
import ru.shelter.dto.response.MessageResponse;
import ru.shelter.interfaces.ChatRepo;
import ru.shelter.interfaces.MessageRepo;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.MessageMapper;
import ru.shelter.model.Message;
import ru.shelter.serviceInterfaces.MessageInterface;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageService implements MessageInterface {

    private final MessageRepo messageRepo;
    private final UserRepo userRepo;
    private final ChatRepo chatRepo;
    private final MessageMapper messageMapper;
    private final ImageStorageService imageStorage;

    @Override
    public MessageResponse add(Long chatId, MessageRequest requestDto, MultipartFile image) {
        try {
            Message message = messageMapper.fromDto(requestDto); // создание entity через маппер
            if (imageStorage.validateImage(image))
                message.setImageAddress(imageStorage.saveImage(image));
            userRepo.findById(requestDto.authorId()).orElseThrow(); // ДОЛЖНО ВЫБРОСИТЬ ИСКЛЮЧЕНИЕ О ТОМ, ЧТО НЕ НАШЛО ЗАЯВЛЕННОГО АВТОРА
            chatRepo.findById(chatId).orElseThrow(); // ТО ЖЕ, НО С ЧАТОМ
            message.setChatId(chatId);
            Message savedMessage = messageRepo.save(message); // сохраняем сообщение в бд

            MessageResponse response = messageMapper.toMessageResponse(savedMessage); // готовим ответ на запрос
            log.info("Adding message: {}", response);
            return response;
        }
        catch (Exception e)
        {
            log.error("Add Message error:", e);
            throw e;
        }
    }



    @Override
    public Optional<MessageResponse> get(Long id) {
        try {
            Optional<MessageResponse> response = messageRepo.findById(id).map(messageMapper::toMessageResponse);
            if (response.isPresent())
                log.info("Get message:", response);
            else
                log.info("Message with id", id, "not found");
            return response;
        }
        catch (Exception e)
        {
            log.error ("Get message error:", e);
            throw e;
        }
    }


    @Override
    public MessageResponse update(MessageRequest requestDto, Long id, MultipartFile image) {
        try {
            Optional<Message> Message = messageRepo.findById(id);
            if (Message.isPresent())
            {
                Message message = messageMapper.fromDto(requestDto);
                message.setId(id);
                if (imageStorage.validateImage(image))
                    message.setImageAddress(imageStorage.saveImage(image));
                messageRepo.save(message);
                log.info("Updating message: {}", requestDto);
                return (messageMapper.toMessageResponse(message));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update message error:", e);
            throw e;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            messageRepo.deleteById(id);
            log.info("Deleted message: {}", id);
            return true;
        }
        catch (Exception e)
        {
            log.error("Deleting message error:", e);
            throw e;
        }
    }

    @Override
    public List<MessageResponse> getAll() {
        try
        {
            List<Message> messages = messageRepo.findAll();
            log.info("GetAll messages successfully:{}", messages);
            return (messageMapper.toMessageResponseList(messages));
        }
        catch (Exception e)
        {
            log.error("GetAll message error:", e);
            return Collections.emptyList();
        }
    }

    public List<MessageResponse> getAllByChatId(Long chatId) {
        try
        {
            List<Message> messages = messageRepo.findAllByChatId(chatId);
            log.info("GetAll messages successfully:{}", messages);
            return (messageMapper.toMessageResponseList(messages));
        }
        catch (Exception e)
        {
            log.error("GetAll message error:", e);
            throw e;
        }
    }

    public List<MessageResponse> getAllByUserId(Long userId) {
        try
        {
            List<Message> messages = messageRepo.findAllByUserId(userId);
            log.info("GetAll messages successfully:{}", messages);
            return (messageMapper.toMessageResponseList(messages));
        }
        catch (Exception e)
        {
            log.error("GetAll message error:", e);
            throw e;
        }
    }

    public List<MessageResponse> getAllByChatIdAndUserId(Long chatId, Long userId) {
        try
        {
            List<Message> messages = messageRepo.findAllByChatIdAndUserId(chatId, userId);
            log.info("GetAll messages successfully:{}", messages);
            return (messageMapper.toMessageResponseList(messages));
        }
        catch (Exception e)
        {
            log.error("GetAll message error:", e);
            throw e;
        }
    }

}

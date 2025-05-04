package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageCreateRequestDto;
import ru.shelter.dto.response.MessageResponseDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.interfaces.MessageDao;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.MessageMapper;
import ru.shelter.model.Chat;
import ru.shelter.model.Message;
import ru.shelter.model.User;
import ru.shelter.serviceInterfaces.MessageService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageImpl implements MessageService {

    private final MessageDao messageDao;
    private final UserDao userDao;
    private final ChatDao chatDao;
    private final MessageMapper messageMapper;
    private final ImageStorageImpl imageStorage;

    @Override
    public MessageResponseDto add(MessageCreateRequestDto requestDto, MultipartFile image) {
        try {
            Message message = messageMapper.fromDto(requestDto); // создание entity через маппер
            if (imageStorage.validateImage(image))
                message.setImageAddress(imageStorage.saveImage(image));
            User author = userDao.findById(requestDto.userId()).orElseThrow(); // ДОЛЖНО ВЫБРОСИТЬ ИСКЛЮЧЕНИЕ О ТОМ, ЧТО НЕ НАШЛО ЗАЯВЛЕННОГО АВТОРА
            Chat chat = chatDao.findById(requestDto.chatId()).orElseThrow(); // ТО ЖЕ, НО С ЧАТОМ
            Message savedMessage = messageDao.save(message); // сохраняем сообщение в бд
            author.addMessage(savedMessage); // Добавляем автору новое сообщение, там же сообщению выставится автор
            chat.addMessage(savedMessage);
            MessageResponseDto response = messageMapper.toMessageResponse(savedMessage); // готовим ответ на запрос
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
    public Optional<MessageResponseDto> get(Long id) {
        try {
            Optional<MessageResponseDto> response = messageDao.findById(id).map(messageMapper::toMessageResponse);
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
    public MessageResponseDto update(MessageCreateRequestDto requestDto, Long id, MultipartFile image) {
        try {
            Optional<Message> Message = messageDao.findById(id);
            if (Message.isPresent())
            {
                Message message = messageMapper.fromDto(requestDto);
                message.setId(id);
                if (imageStorage.validateImage(image))
                    message.setImageAddress(imageStorage.saveImage(image));
                messageDao.save(message);
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
            messageDao.deleteById(id);
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
    public List<MessageResponseDto> getAll() {
        try
        {
            List<Message> messages = messageDao.findAll();
            log.info("GetAll messages successfully:{}", messages);
            return (messageMapper.toMessageResponseList(messages));
        }
        catch (Exception e)
        {
            log.error("GetAll message error:", e);
            return Collections.emptyList();
        }
    }
}

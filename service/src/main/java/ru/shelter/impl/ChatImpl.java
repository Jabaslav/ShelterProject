package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatResponseDto;
import ru.shelter.exception.ImageFileException;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.mapper.ChatMapper;
import ru.shelter.model.Chat;
import ru.shelter.model.ChatParticipant;
import ru.shelter.serviceInterfaces.ChatService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatImpl implements ChatService {
    private final ChatMapper chatMapper;
    private final ChatDao chatDao;
    private final ImageStorageImpl imageStorage;
    private final ChatParticipantImpl chatParticipantImpl;

    @Override
    public ChatResponseDto add(ChatCreateRequestDto requestDto, MultipartFile image) {
        try {
            if (image == null || image.isEmpty() || !image.getContentType().startsWith("image/")) {
                throw new ImageFileException("Не удалось считать изображение из файла");
            }

            Chat chat = chatMapper.fromDto(requestDto); // создание entity через маппер, пароль не заполняется
            chat.setImageAddress(imageStorage.saveImage(image));
            chatDao.save(chat); // сохраняем чат в бд
            ChatResponseDto response = chatMapper.toChatResponse(chat);
            chatParticipantImpl.add(new ChatParticipantRequestDto(response.id(), requestDto.userId(), ChatParticipant.ParticipantRole.admin));
            log.info("Adding chat: {}", response);
            return response;
        } catch (Exception e)
        {
            log.error("Add chat error:", e);
            throw e;
        }
    }


    @Override
    public Optional<ChatResponseDto> get(Long id) {
        try {
            Optional<ChatResponseDto> response = chatDao.findById(id).map(chatMapper::toChatResponse);
            if (response.isPresent())
                log.info("Get chat:", response);
            else
                log.info("Chat with id", id, "not found");
            return response;
        }
        catch (Exception e)
        {
            log.error ("Get chat error:", e);
            return Optional.empty();
        }
    }

    @Override
    public ChatResponseDto update(ChatCreateRequestDto requestDto, Long id) {
        try {
            Optional<Chat> chat = chatDao.findById(id);
            if (chat.isPresent())
            {
                Chat updatedChat = chatMapper.fromDto(requestDto);
                updatedChat.setId(id);

                chatDao.save(updatedChat);
                log.info("Updating chat: {}", requestDto);
                return (chatMapper.toChatResponse(updatedChat));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update chat error:", e);
            return null;
        }
    }

    @Override
    public ChatResponseDto updateWithImage(ChatCreateRequestDto requestDto, Long id, MultipartFile image) {
        try {
            Optional<Chat> chat = chatDao.findById(id);
            if (chat.isPresent())
            {
                Chat updatedChat = chatMapper.fromDto(requestDto);
                updatedChat.setId(id);
                if (image !=null && !image.isEmpty())
                {
                    updatedChat.setImageAddress(imageStorage.saveImage(image));
                }
                chatDao.save(updatedChat);
                log.info("Updating chat: {}", requestDto);
                return (chatMapper.toChatResponse(updatedChat));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update chat error:", e);
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            chatDao.deleteById(id);
            log.info("Deleted chat: {}", id);
            return true;
        }
        catch (Exception e)
        {
            log.error("Deleting chat error:", e);
            return false;
        }
    }

    @Override
    public List<ChatResponseDto> getAll() {
        try
        {
            List<Chat> chats = chatDao.findAll();
            log.info("GetAll chats successfully:{}", chats);
            return (chatMapper.toChatResponseList(chats));
        }
        catch (Exception e)
        {
            log.error("GetAll chat error:", e);
            return Collections.emptyList();
        }
    }
}

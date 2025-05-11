package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatMemberRequest;
import ru.shelter.dto.request.ChatRequest;
import ru.shelter.dto.response.ChatResponse;
import ru.shelter.interfaces.ChatRepo;
import ru.shelter.mapper.ChatMapper;
import ru.shelter.model.Chat;
import ru.shelter.model.ChatMember;
import ru.shelter.serviceInterfaces.ChatInterface;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatService implements ChatInterface {
    private final ChatMapper chatMapper;
    private final ChatRepo chatRepo;
    private final ImageStorageService imageStorage;
    private final ChatMemberService chatMemberService;

    @Override
    public ChatResponse add(ChatRequest requestDto, MultipartFile image) {
        try {
            Chat chat = chatMapper.fromDto(requestDto); // создание entity через маппер, пароль не заполняется
            if (imageStorage.validateImage(image))
                chat.setImageAddress(imageStorage.saveImage(image));
            chatRepo.save(chat); // сохраняем чат в бд
            ChatResponse response = chatMapper.toChatResponse(chat);
            chatMemberService.add(new ChatMemberRequest(response.id(), requestDto.userId(), ChatMember.ParticipantRole.ADMIN));
            log.info("Adding chat: {}", response);
            return response;
        } catch (Exception e)
        {
            log.error("Add chat error:", e);
            throw e;
        }
    }


    @Override
    public Optional<ChatResponse> get(Long id) {
        try {
            Optional<ChatResponse> response = chatRepo.findById(id).map(chatMapper::toChatResponse);
            if (response.isPresent())
                log.info("Get chat:", response);
            else
                log.info("Chat with id", id, "not found");
            return response;
        }
        catch (Exception e)
        {
            log.error ("Get chat error:", e);
            throw e;
        }
    }


    @Override
    public ChatResponse update(ChatRequest requestDto, Long id, MultipartFile image) {
        try {
            Optional<Chat> chat = chatRepo.findById(id);
            if (chat.isPresent())
            {
                Chat updatedChat = chatMapper.fromDto(requestDto);
                updatedChat.setId(id);
                if (imageStorage.validateImage(image))
                {
                    updatedChat.setImageAddress(imageStorage.saveImage(image));
                }
                chatRepo.save(updatedChat);
                log.info("Updating chat: {}", requestDto);
                return (chatMapper.toChatResponse(updatedChat));
            }
            else
                throw new EntityNotFoundException();
        }
        catch (Exception e)
        {
            log.error("Update chat error:", e);
            throw e;
        }
    }

    @Override
    public boolean remove(Long id) {
        try {
            chatRepo.deleteById(id);
            log.info("Deleted chat: {}", id);
            return true;
        }
        catch (Exception e)
        {
            log.error("Deleting chat error:", e);
            throw e;
        }
    }

    @Override
    public List<ChatResponse> getAll() {
        try
        {
            List<Chat> chats = chatRepo.findAll();
            log.info("GetAll chats successfully:{}", chats);
            return (chatMapper.toChatResponseList(chats));
        }
        catch (Exception e)
        {
            log.error("GetAll chat error:", e);
            throw e;
        }
    }
}

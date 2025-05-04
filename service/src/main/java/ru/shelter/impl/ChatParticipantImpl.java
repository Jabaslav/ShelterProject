package ru.shelter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shelter.dto.request.ChatParticipantRequestDto;
import ru.shelter.dto.response.ChatParticipantResponseDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.interfaces.ChatParticipantDao;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.ChatMapper;
import ru.shelter.mapper.ChatParticipantMapper;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.ChatParticipant;
import ru.shelter.model.ChatParticipantId;
import ru.shelter.serviceInterfaces.ChatParticipantService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatParticipantImpl implements ChatParticipantService {
    private final ChatParticipantMapper chatParticipantMapper;
    private final ChatParticipantDao chatParticipantDao;
    private final ChatDao chatDao;
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final ChatMapper chatMapper;


    @Override
    public List<ChatParticipantResponseDto> findUsersByChatId(Long chatId) {
        try {
            return chatParticipantMapper.toChatParticipantResponseList(chatParticipantDao.findUsersByChatId(chatId).orElseThrow());
        } catch (Exception e) {
            log.error("Error finding chatParticipants by chat:", e);
            throw e;
        }
    }

    @Override
    public List<ChatParticipantResponseDto> findChatsByUserId(Long userId) {
        try {
            return chatParticipantMapper.toChatParticipantResponseList(chatParticipantDao.findChatsByUserId(userId).orElseThrow());
        } catch (Exception e) {
            log.error("Error finding chats by chatParticipant:", e);
            throw e;
        }
    }

    @Override
    public ChatParticipantResponseDto add(ChatParticipantRequestDto requestDto) {
        try {
            ChatParticipant chatParticipant = chatParticipantMapper.fromDto(requestDto);
            chatParticipant.setUser(userDao.findById(requestDto.userId()).orElseThrow());
            chatParticipant.setChat(chatDao.findById(requestDto.chatId()).orElseThrow());
            ChatParticipant savedChatParticipant = chatParticipantDao.save(chatParticipant);
            log.info("Added chatParticipant: {}", chatParticipant);
            return chatParticipantMapper.toChatParticipantResponse(savedChatParticipant);
        } catch (Exception e) {
            log.error("Error adding chatParticipant:", e);
            throw e;
        }
    }

    @Override
    public Optional<ChatParticipantResponseDto> get(ChatParticipantId chatParticipantId) {
        try {
            return Optional.ofNullable(chatParticipantMapper.toChatParticipantResponse
                    (chatParticipantDao.findById(chatParticipantId).orElseThrow()));
        } catch (NoSuchElementException e){
            log.error("No chatParticipant with id {}", chatParticipantId);
            throw e;
        } catch (Exception e) {
            log.error("Error finding chats by chatParticipant:", e);
            throw e;
        }
    }

    @Override
    public ChatParticipantResponseDto update(ChatParticipantRequestDto requestDto, ChatParticipantId chatParticipantId) {
        try {
            chatParticipantDao.findById(chatParticipantId).orElseThrow();
            ChatParticipant updatedChatParticipant = chatParticipantMapper.fromDto(requestDto);
            chatParticipantDao.save(updatedChatParticipant);
            log.info("Updating chatParticipant: {}", chatParticipantId);
            return (chatParticipantMapper.toChatParticipantResponse(updatedChatParticipant));
        } catch (NoSuchElementException e){
        log.error("No chatParticipant with id {}", chatParticipantId);
            throw e;
        } catch (Exception e)
        {
            log.error("Update chatParticipant error:", e);
            return null;
        }
    }

    @Override
    public boolean remove(ChatParticipantId chatParticipantId) {
        return false;
    }

    @Override
    public ArrayList<ChatParticipantResponseDto> getAll() {
        return null;
    }
}

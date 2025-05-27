package ru.shelter.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shelter.dto.request.ChatMemberRequest;
import ru.shelter.dto.response.ChatMemberResponse;
import ru.shelter.exception.ValidationException;
import ru.shelter.interfaces.ChatRepo;
import ru.shelter.interfaces.ChatMemberRepo;
import ru.shelter.interfaces.UserRepo;
import ru.shelter.mapper.ChatMapper;
import ru.shelter.mapper.ChatMemberMapper;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.ChatMember;
import ru.shelter.model.ChatMemberId;
import ru.shelter.serviceInterfaces.ChatMemberInterface;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMemberService implements ChatMemberInterface {
    private final ChatMemberMapper chatMemberMapper;
    private final ChatMemberRepo chatMemberRepo;
    private final ChatRepo chatRepo;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final ChatMapper chatMapper;


    @Override
    public List<ChatMemberResponse> findUsersByChatId(Long chatId) {
        try {
            return chatMemberMapper.toChatParticipantResponseList(chatMemberRepo.findUsersByChatId(chatId).orElseThrow());
        } catch (Exception e) {
            log.error("Error finding chatParticipants by chat:", e);
            throw e;
        }
    }

    @Override
    public List<ChatMemberResponse> findChatsByUserId(Long userId) {
        try {
            return chatMemberMapper.toChatParticipantResponseList(chatMemberRepo.findChatsByUserId(userId).orElseThrow());
        } catch (Exception e) {
            log.error("Error finding chats by chatParticipant:", e);
            throw e;
        }
    }

    @Override
    public ChatMemberResponse add(ChatMemberRequest requestDto) {
        try {
            ChatMember chatMember = chatMemberMapper.fromDto(requestDto);
            chatMember.setUser(userRepo.findById(requestDto.userId()).orElseThrow(()-> new ValidationException("No user found with id: " + requestDto.userId())));
            chatMember.setChat(chatRepo.findById(requestDto.chatId()).orElseThrow(()-> new ValidationException("No chat found with id: " + requestDto.chatId())));
            ChatMember savedChatMember = chatMemberRepo.save(chatMember);
            log.info("Added chatParticipant: {}", chatMember);
            return chatMemberMapper.toChatParticipantResponse(savedChatMember);
        } catch (Exception e) {
            log.error("Error adding chatParticipant:", e);
            throw e;
        }
    }

    @Override
    public Optional<ChatMemberResponse> get(ChatMemberId chatMemberId) {
        try {
            return Optional.ofNullable(chatMemberMapper.toChatParticipantResponse
                    (chatMemberRepo.findById(chatMemberId).orElseThrow()));
        } catch (NoSuchElementException e){
            log.error("No chatParticipant with id {}", chatMemberId);
            throw e;
        } catch (Exception e) {
            log.error("Error finding chats by chatParticipant:", e);
            throw e;
        }
    }

    @Override
    public ChatMemberResponse update(ChatMemberRequest requestDto) {
        try {
            ChatMemberId chatMemberId = new ChatMemberId(requestDto.chatId(), requestDto.userId());
            chatMemberRepo.findById(chatMemberId).orElseThrow(()->new ValidationException("Нет такого участника в заданном чате"));
            ChatMember updatedChatMember = chatMemberMapper.fromDto(requestDto);
            chatMemberRepo.save(updatedChatMember);
            log.info("Updating chatParticipant: {}", chatMemberId);
            return (chatMemberMapper.toChatParticipantResponse(updatedChatMember));
        } catch (NoSuchElementException e) {
            log.error("No chatParticipant found with given id");
            throw e;
        } catch (Exception e) {
            log.error("Update chatParticipant error:", e);
            throw e;
        }
    }

    @Override
    public boolean remove(ChatMemberId chatMemberId) {
        return false;
    }

    @Deprecated
    @Override
    public List<ChatMemberResponse> getAll() {
        return null;
    }

    public List<ChatMemberResponse> getAllByChatId(Long chatId) {
        return chatMemberMapper.toChatParticipantResponseList(chatMemberRepo.findUsersByChatId(chatId).orElse(Collections.emptyList()));
    }

    @Override
    public boolean isUserInChat(Long userId, Long chatId)
    {
        try{
            chatMemberRepo.isUserInChat(userId, chatId).orElseThrow(() -> new EntityNotFoundException("Пользователя в таком чате не найдено"));
            return true;
        } catch (Exception e)
        {
            log.info("IsUserInChat error,  {}", e.getMessage());
            return false;
        }
    }
}

package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatMemberRequest;
import ru.shelter.dto.request.ChatRequest;
import ru.shelter.dto.request.MessageRequest;
import ru.shelter.dto.response.ChatMemberResponse;
import ru.shelter.dto.response.ChatResponse;
import ru.shelter.dto.response.MessageResponse;
import ru.shelter.impl.ChatService;
import ru.shelter.impl.ChatMemberService;
import ru.shelter.impl.MessageService;
import ru.shelter.model.ChatMember;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatMemberService chatParticipantService;
    private final MessageService messageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatResponse> createChat(
            @Valid @RequestPart(value = "chatDto") ChatRequest requestDto,
            @RequestPart(value="image", required = false) MultipartFile image
    ) {
        return new ResponseEntity<>(chatService.add(requestDto, image), HttpStatus.CREATED); // Http 201
    }

    @GetMapping("/{chat_id}")
    public ResponseEntity<?> getChatById(@PathVariable("chat_id") Long chatId) {
        return new ResponseEntity<>(chatService.get(chatId), HttpStatus.OK); // Http 200
    }

    @PutMapping(path = "/{chat_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateChat(@PathVariable("chat_id") Long chatId,
                                        @Valid @RequestPart("chat") ChatRequest requestDto,
                                        @RequestPart(value = "image", required = false) MultipartFile image) {
        return new ResponseEntity<>(chatService.update(requestDto, chatId, image), HttpStatus.OK); // Http 200
    }

    @PostMapping(path = "/{chat_id}/members")
    public ResponseEntity<ChatMemberResponse> addChatMember(
            @PathVariable("chat_id") Long chatId,
            @Valid @RequestBody ChatMemberRequest request)
    {
        return new ResponseEntity<>(chatParticipantService.add(request), HttpStatus.CREATED); // Http 201
    }

    @GetMapping(path = "/{chat_id}/members")
    public ResponseEntity<?> getChatMembers(
            @PathVariable("chat_id") Long chatId)
    {
        return new ResponseEntity<>(chatParticipantService.getAllByChatId(chatId), HttpStatus.OK); // Http 200
    }



    @PutMapping(path = "/{chat_id}/participants/{user_id}")
    public ResponseEntity<ChatMemberResponse> updateChatParticipant(
            @PathVariable("chat_id") Long chatId,
            @PathVariable("user_id") Long userId,
            @RequestBody ChatMember.ParticipantRole role)
    {
        return new ResponseEntity<>(chatParticipantService.update(new ChatMemberRequest(chatId, userId, role)), HttpStatus.OK); // Http 200
    }

    @DeleteMapping("/{chat_id}")
    public ResponseEntity<?> deleteChat(@PathVariable("chat_id") Long chatId) {
        return new ResponseEntity<>(chatService.remove(chatId), HttpStatus.NO_CONTENT); // Http 204
    }


    @GetMapping
    public ResponseEntity<List<ChatResponse>> getAllChats() {
        return new ResponseEntity<>(chatService.getAll(), HttpStatus.OK); // Http 200
    }
}

package ru.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageRequest;
import ru.shelter.dto.response.MessageResponse;
import ru.shelter.impl.MessageService;

@RestController
@RequestMapping("/api/chats/{chat_id}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> addMessageToChat(
            @PathVariable("chat_id") Long chatId,
            @RequestPart("messageDto") MessageRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image)
    {
        return new ResponseEntity<>(messageService.add(chatId, request, image), HttpStatus.CREATED); // Http 201
    }

    @GetMapping
    public ResponseEntity<?> getMessageHistory(@PathVariable("chat_id") Long chatId)
    {
        return new ResponseEntity<>(messageService.getAllByChatId(chatId), HttpStatus.OK); // Http 200
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getAllMessagesFromChatByMember(@PathVariable("chat_id") Long chatId,
                                                            @PathVariable("user_id") Long userId)
    {
        return new ResponseEntity<>(messageService.getAllByChatIdAndUserId(chatId, userId), HttpStatus.OK); // Http 200
    }
}

package ru.shelter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageRequest;
import ru.shelter.dto.response.MessageResponse;
import ru.shelter.impl.ChatMemberService;
import ru.shelter.impl.ChatService;
import ru.shelter.impl.MessageService;

@RestController
@RequestMapping("/api/chats/{chat_id}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final ChatMemberService memberService;

    @PreAuthorize("@chatMemberService.isUserInChat(principal.id, #chatId) and principal.id == #request.authorId")
    @PostMapping
    public ResponseEntity<MessageResponse> addMessageToChat(
            @PathVariable("chat_id") Long chatId,
            @Valid @RequestPart("messageDto") MessageRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image)
    {
        return new ResponseEntity<>(messageService.add(chatId, request, image), HttpStatus.CREATED); // Http 201
    }

    @PreAuthorize("@chatMemberService.isUserInChat(principal.id, #chatId)")
    @GetMapping
    public ResponseEntity<?> getMessageHistory(@PathVariable("chat_id") Long chatId)
    {
        return new ResponseEntity<>(messageService.getAllByChatId(chatId), HttpStatus.OK); // Http 200
    }

    @PreAuthorize("@chatMemberService.isUserInChat(principal.id, #chatId)")
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getAllMessagesFromChatByMember(@PathVariable("chat_id") Long chatId,
                                                            @PathVariable("user_id") Long userId)
    {
        return new ResponseEntity<>(messageService.getAllByChatIdAndUserId(chatId, userId), HttpStatus.OK); // Http 200
    }

//    @DeleteMapping
//    public ResponseEntity<?> deleteMessage()
//    {
//        return new ResponseEntity<>(messageService.remove())
//    }
}

package ru.shelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.response.ChatResponseDto;
import ru.shelter.impl.ChatImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatImpl chatService;

    @PostMapping
    public ChatResponseDto createChat(@RequestBody ChatCreateRequestDto requestDto) {
        return chatService.add(requestDto);
    }

    @PostMapping("/with-image")
    public ChatResponseDto createChatWithImage(@RequestPart("chat") ChatCreateRequestDto requestDto,
                                               @RequestPart("image") MultipartFile image) {
        return chatService.addWithImage(requestDto, image);
    }

    @GetMapping("/{id}")
    public Optional<ChatResponseDto> getChatById(@PathVariable Long id) {
        return chatService.get(id);
    }

    @PutMapping("/{id}")
    public ChatResponseDto updateChat(@PathVariable Long id, @RequestBody ChatCreateRequestDto requestDto) {
        return chatService.update(requestDto, id);
    }

    @PutMapping("/{id}/with-image")
    public ChatResponseDto updateChatWithImage(@PathVariable Long id,
                                               @RequestPart("chat") ChatCreateRequestDto requestDto,
                                               @RequestPart("image") MultipartFile image) {
        return chatService.updateWithImage(requestDto, id, image);
    }

    @DeleteMapping("/{id}")
    public boolean deleteChat(@PathVariable Long id) {
        return chatService.remove(id);
    }

    @GetMapping
    public List<ChatResponseDto> getAllChats() {
        return chatService.getAll();
    }
}

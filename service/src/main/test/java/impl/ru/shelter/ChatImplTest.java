package ru.shelter.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.ChatCreateRequestDto;
import ru.shelter.dto.response.ChatResponseDto;
import ru.shelter.interfaces.ChatDao;
import ru.shelter.mapper.ChatMapper;
import ru.shelter.model.Chat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatImplTest {

    @Mock
    private ChatDao chatDao;

    @Mock
    private ChatMapper chatMapper;

    @Mock
    private ImageStorageImpl imageStorage;

    @InjectMocks
    private ChatImpl chatService;

    private final LocalDateTime testCreationTime = LocalDateTime.now();
    private final String testImagePath = "/uploads/test_chat.jpg";

    private ChatCreateRequestDto createTestRequestDto() {
        return new ChatCreateRequestDto("Test Chat");
    }

    private Chat createTestChat(Long id, boolean withImage) {
        Chat chat = new Chat();
        chat.setId(id);
        chat.setName("Test Chat");
        chat.setCreationTime(testCreationTime);
        if (withImage) {
            chat.setImageAddress(testImagePath);
        }
        return chat;
    }

    private ChatResponseDto createTestResponseDto(Long id, boolean withImage) {
        return new ChatResponseDto(
                id,
                "Test Chat",
                withImage ? testImagePath : null,
                testCreationTime
        );
    }

    @Test
    void add_ShouldReturnChatResponse_WhenSuccess() {
        // Arrange
        ChatCreateRequestDto requestDto = createTestRequestDto();
        Chat chat = createTestChat(null, false);
        Chat savedChat = createTestChat(1L, false);
        ChatResponseDto expectedResponse = createTestResponseDto(1L, false);

        when(chatMapper.fromDto(requestDto)).thenReturn(chat);
        when(chatDao.save(chat)).thenReturn(savedChat);
        when(chatMapper.toChatResponse(savedChat)).thenReturn(expectedResponse);

        // Act
        ChatResponseDto actualResponse = chatService.add(requestDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(chatDao).save(chat);
    }

    @Test
    void addWithImage_ShouldReturnChatResponseWithImage_WhenSuccess() {
        // Arrange
        ChatCreateRequestDto requestDto = createTestRequestDto();
        MultipartFile image = new MockMultipartFile(
                "image",
                "test_chat.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        Chat chat = createTestChat(null, false);
        Chat savedChat = createTestChat(1L, true);
        ChatResponseDto expectedResponse = createTestResponseDto(1L, true);

        when(chatMapper.fromDto(requestDto)).thenReturn(chat);
        when(imageStorage.saveImage(image)).thenReturn(testImagePath);
        when(chatDao.save(chat)).thenReturn(savedChat);
        when(chatMapper.toChatResponse(savedChat)).thenReturn(expectedResponse);

        // Act
        ChatResponseDto actualResponse = chatService.addWithImage(requestDto, image);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.imageAddress());
        verify(imageStorage).saveImage(image);
    }

    @Test
    void get_ShouldReturnChatResponse_WhenChatExists() {
        // Arrange
        Long chatId = 1L;
        Chat chat = createTestChat(chatId, true);
        ChatResponseDto expectedResponse = createTestResponseDto(chatId, true);

        when(chatDao.findById(chatId)).thenReturn(Optional.of(chat));
        when(chatMapper.toChatResponse(chat)).thenReturn(expectedResponse);

        // Act
        Optional<ChatResponseDto> actualResponse = chatService.get(chatId);

        // Assert
        assertTrue(actualResponse.isPresent());
        assertEquals(expectedResponse, actualResponse.get());
    }

    @Test
    void update_ShouldReturnUpdatedChat_WhenChatExists() {
        // Arrange
        Long chatId = 1L;
        ChatCreateRequestDto requestDto = new ChatCreateRequestDto("Updated Chat Name");
        Chat existingChat = createTestChat(chatId, false);
        Chat updatedChat = createTestChat(chatId, false);
        updatedChat.setName("Updated Chat Name");
        ChatResponseDto expectedResponse = new ChatResponseDto(
                chatId,
                "Updated Chat Name",
                null,
                testCreationTime
        );

        when(chatDao.findById(chatId)).thenReturn(Optional.of(existingChat));
        when(chatMapper.fromDto(requestDto)).thenReturn(updatedChat);
        when(chatDao.save(updatedChat)).thenReturn(updatedChat);
        when(chatMapper.toChatResponse(updatedChat)).thenReturn(expectedResponse);

        // Act
        ChatResponseDto actualResponse = chatService.update(requestDto, chatId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals("Updated Chat Name", actualResponse.name());
    }

    @Test
    void updateWithImage_ShouldUpdateImage_WhenNewImageProvided() {
        // Arrange
        Long chatId = 1L;
        ChatCreateRequestDto requestDto = createTestRequestDto();
        MultipartFile newImage = new MockMultipartFile(
                "newImage",
                "new_image.jpg",
                "image/jpeg",
                "new image content".getBytes()
        );

        Chat existingChat = createTestChat(chatId, false);
        Chat updatedChat = createTestChat(chatId, true);
        ChatResponseDto expectedResponse = createTestResponseDto(chatId, true);

        when(chatDao.findById(chatId)).thenReturn(Optional.of(existingChat));
        when(chatMapper.fromDto(requestDto)).thenReturn(updatedChat);
        when(imageStorage.saveImage(newImage)).thenReturn(testImagePath);
        when(chatDao.save(updatedChat)).thenReturn(updatedChat);
        when(chatMapper.toChatResponse(updatedChat)).thenReturn(expectedResponse);

        // Act
        ChatResponseDto actualResponse = chatService.updateWithImage(requestDto, chatId, newImage);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.imageAddress());
    }

    @Test
    void remove_ShouldReturnTrue_WhenChatExists() {
        // Arrange
        Long chatId = 1L;
        doNothing().when(chatDao).deleteById(chatId);

        // Act
        boolean result = chatService.remove(chatId);

        // Assert
        assertTrue(result);
        verify(chatDao).deleteById(chatId);
    }

    @Test
    void getAll_ShouldReturnListOfChats() {
        // Arrange
        Chat chat1 = createTestChat(1L, true);
        Chat chat2 = createTestChat(2L, false);
        List<Chat> chats = List.of(chat1, chat2);

        ChatResponseDto response1 = createTestResponseDto(1L, true);
        ChatResponseDto response2 = createTestResponseDto(2L, false);
        List<ChatResponseDto> expected = List.of(response1, response2);

        when(chatDao.findAll()).thenReturn(chats);
        when(chatMapper.toChatResponseList(chats)).thenReturn(expected);

        // Act
        List<ChatResponseDto> result = chatService.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(expected, result);
    }

    @Test
    void addWithImage_ShouldNotSaveImage_WhenImageIsNull() {
        // Arrange
        ChatCreateRequestDto requestDto = createTestRequestDto();
        Chat chat = createTestChat(null, false);
        Chat savedChat = createTestChat(1L, false);
        ChatResponseDto expectedResponse = createTestResponseDto(1L, false);

        when(chatMapper.fromDto(requestDto)).thenReturn(chat);
        when(chatDao.save(chat)).thenReturn(savedChat);
        when(chatMapper.toChatResponse(savedChat)).thenReturn(expectedResponse);

        // Act
        ChatResponseDto actualResponse = chatService.addWithImage(requestDto, null);

        // Assert
        assertNotNull(actualResponse);
        assertNull(actualResponse.imageAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void updateWithImage_ShouldKeepExistingImage_WhenNoNewImageProvided() {
        // Arrange
        Long chatId = 1L;
        String existingImagePath = "/uploads/existing.jpg";
        ChatCreateRequestDto requestDto = createTestRequestDto();
        Chat existingChat = createTestChat(chatId, true);
        existingChat.setImageAddress(existingImagePath);

        Chat updatedChat = createTestChat(chatId, true);
        updatedChat.setImageAddress(existingImagePath);

        ChatResponseDto expectedResponse = new ChatResponseDto(
                chatId,
                "Test Chat",
                existingImagePath,
                testCreationTime
        );

        when(chatDao.findById(chatId)).thenReturn(Optional.of(existingChat));
        when(chatMapper.fromDto(requestDto)).thenReturn(updatedChat);
        when(chatDao.save(updatedChat)).thenReturn(updatedChat);
        when(chatMapper.toChatResponse(updatedChat)).thenReturn(expectedResponse);

        // Act
        ChatResponseDto actualResponse = chatService.updateWithImage(requestDto, chatId, null);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(existingImagePath, actualResponse.imageAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void add_ShouldReturnNull_WhenExceptionThrown() {
        // Arrange
        ChatCreateRequestDto requestDto = createTestRequestDto();
        when(chatMapper.fromDto(requestDto)).thenThrow(new RuntimeException("Test exception"));

        // Act
        ChatResponseDto response = chatService.add(requestDto);

        // Assert
        assertNull(response);
    }

    @Test
    void get_ShouldReturnEmptyOptional_WhenChatNotExists() {
        // Arrange
        Long chatId = 999L;
        when(chatDao.findById(chatId)).thenReturn(Optional.empty());

        // Act
        Optional<ChatResponseDto> response = chatService.get(chatId);

        // Assert
        assertTrue(response.isEmpty());
    }
}
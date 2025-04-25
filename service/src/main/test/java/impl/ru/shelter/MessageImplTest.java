package ru.shelter.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.MessageCreateRequestDto;
import ru.shelter.dto.response.MessageResponseDto;
import ru.shelter.interfaces.MessageDao;
import ru.shelter.mapper.MessageMapper;
import ru.shelter.model.Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageImplTest {

    @Mock
    private MessageDao messageDao;

    @Mock
    private MessageMapper messageMapper;

    @Mock
    private ImageStorageImpl imageStorage;

    @InjectMocks
    private MessageImpl messageService;

    private final LocalDateTime testCreationTime = LocalDateTime.now();
    private final String testImagePath = "/uploads/test_message.jpg";

    private MessageCreateRequestDto createTestRequestDto(String text) {
        return new MessageCreateRequestDto(
                1L, // chatId
                1L, // authorId
                text
        );
    }

    private Message createTestMessage(Long id, boolean withImage, String text) {
        Message message = new Message();
        message.setId(id);
        message.setText(text);
        message.setCreationTime(testCreationTime);
        if (withImage) {
            message.setImageAddress(testImagePath);
        }
        return message;
    }

    private MessageResponseDto createTestResponseDto(Long id, boolean withImage, String text) {
        return new MessageResponseDto(
                id,
                1L, // chatId
                1L, // authorId
                withImage ? testImagePath : null,
                text,
                testCreationTime
        );
    }

    @Test
    void add_ShouldReturnMessageResponse_WhenSuccess() {
        // Arrange
        String testText = "Test message text";
        MessageCreateRequestDto requestDto = createTestRequestDto(testText);
        Message message = createTestMessage(null, false, testText);
        Message savedMessage = createTestMessage(1L, false, testText);
        MessageResponseDto expectedResponse = createTestResponseDto(1L, false, testText);

        when(messageMapper.fromDto(requestDto)).thenReturn(message);
        when(messageDao.save(message)).thenReturn(savedMessage);
        when(messageMapper.toMessageResponse(savedMessage)).thenReturn(expectedResponse);

        // Act
        MessageResponseDto actualResponse = messageService.add(requestDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(messageDao).save(message);
    }

    @Test
    void addWithImage_ShouldReturnMessageResponseWithImage_WhenSuccess() {
        // Arrange
        String testText = "Test message with image";
        MessageCreateRequestDto requestDto = createTestRequestDto(testText);
        MultipartFile image = new MockMultipartFile(
                "image",
                "test_message.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        Message message = createTestMessage(null, false, testText);
        Message savedMessage = createTestMessage(1L, true, testText);
        MessageResponseDto expectedResponse = createTestResponseDto(1L, true, testText);

        when(messageMapper.fromDto(requestDto)).thenReturn(message);
        when(imageStorage.saveImage(image)).thenReturn(testImagePath);
        when(messageDao.save(message)).thenReturn(savedMessage);
        when(messageMapper.toMessageResponse(savedMessage)).thenReturn(expectedResponse);

        // Act
        MessageResponseDto actualResponse = messageService.addWithImage(requestDto, image);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.imageAddress());
        assertEquals(testText, actualResponse.text());
        verify(imageStorage).saveImage(image);
    }

    @Test
    void get_ShouldReturnMessageResponse_WhenMessageExists() {
        // Arrange
        Long messageId = 1L;
        String testText = "Existing message";
        Message message = createTestMessage(messageId, true, testText);
        MessageResponseDto expectedResponse = createTestResponseDto(messageId, true, testText);

        when(messageDao.findById(messageId)).thenReturn(Optional.of(message));
        when(messageMapper.toMessageResponse(message)).thenReturn(expectedResponse);

        // Act
        Optional<MessageResponseDto> actualResponse = messageService.get(messageId);

        // Assert
        assertTrue(actualResponse.isPresent());
        assertEquals(expectedResponse, actualResponse.get());
    }

    @Test
    void update_ShouldReturnUpdatedMessage_WhenMessageExists() {
        // Arrange
        Long messageId = 1L;
        String updatedText = "Updated message text";
        MessageCreateRequestDto requestDto = createTestRequestDto(updatedText);
        Message existingMessage = createTestMessage(messageId, false, "Original text");
        Message updatedMessage = createTestMessage(messageId, false, updatedText);
        MessageResponseDto expectedResponse = createTestResponseDto(messageId, false, updatedText);

        when(messageDao.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(messageMapper.fromDto(requestDto)).thenReturn(updatedMessage);
        when(messageDao.save(updatedMessage)).thenReturn(updatedMessage);
        when(messageMapper.toMessageResponse(updatedMessage)).thenReturn(expectedResponse);

        // Act
        MessageResponseDto actualResponse = messageService.update(requestDto, messageId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(updatedText, actualResponse.text());
    }

    @Test
    void updateWithImage_ShouldUpdateImage_WhenNewImageProvided() {
        // Arrange
        Long messageId = 1L;
        String testText = "Message with updated image";
        MessageCreateRequestDto requestDto = createTestRequestDto(testText);
        MultipartFile newImage = new MockMultipartFile(
                "newImage",
                "new_image.jpg",
                "image/jpeg",
                "new image content".getBytes()
        );

        Message existingMessage = createTestMessage(messageId, false, testText);
        Message updatedMessage = createTestMessage(messageId, true, testText);
        MessageResponseDto expectedResponse = createTestResponseDto(messageId, true, testText);

        when(messageDao.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(messageMapper.fromDto(requestDto)).thenReturn(updatedMessage);
        when(imageStorage.saveImage(newImage)).thenReturn(testImagePath);
        when(messageDao.save(updatedMessage)).thenReturn(updatedMessage);
        when(messageMapper.toMessageResponse(updatedMessage)).thenReturn(expectedResponse);

        // Act
        MessageResponseDto actualResponse = messageService.updateWithImage(requestDto, messageId, newImage);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.imageAddress());
    }

    @Test
    void remove_ShouldReturnTrue_WhenMessageExists() {
        // Arrange
        Long messageId = 1L;
        doNothing().when(messageDao).deleteById(messageId);

        // Act
        boolean result = messageService.remove(messageId);

        // Assert
        assertTrue(result);
        verify(messageDao).deleteById(messageId);
    }

    @Test
    void getAll_ShouldReturnListOfMessages() {
        // Arrange
        Message message1 = createTestMessage(1L, true, "Message 1");
        Message message2 = createTestMessage(2L, false, "Message 2");
        List<Message> messages = List.of(message1, message2);

        MessageResponseDto response1 = createTestResponseDto(1L, true, "Message 1");
        MessageResponseDto response2 = createTestResponseDto(2L, false, "Message 2");
        List<MessageResponseDto> expected = List.of(response1, response2);

        when(messageDao.findAll()).thenReturn(messages);
        when(messageMapper.toMessageResponseList(messages)).thenReturn(expected);

        // Act
        List<MessageResponseDto> result = messageService.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(expected, result);
    }

    @Test
    void addWithImage_ShouldNotSaveImage_WhenImageIsNull() {
        // Arrange
        String testText = "Message without image";
        MessageCreateRequestDto requestDto = createTestRequestDto(testText);
        Message message = createTestMessage(null, false, testText);
        Message savedMessage = createTestMessage(1L, false, testText);
        MessageResponseDto expectedResponse = createTestResponseDto(1L, false, testText);

        when(messageMapper.fromDto(requestDto)).thenReturn(message);
        when(messageDao.save(message)).thenReturn(savedMessage);
        when(messageMapper.toMessageResponse(savedMessage)).thenReturn(expectedResponse);

        // Act
        MessageResponseDto actualResponse = messageService.addWithImage(requestDto, null);

        // Assert
        assertNotNull(actualResponse);
        assertNull(actualResponse.imageAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void updateWithImage_ShouldKeepExistingImage_WhenNoNewImageProvided() {
        // Arrange
        Long messageId = 1L;
        String existingImagePath = "/uploads/existing.jpg";
        String testText = "Keep existing image";
        MessageCreateRequestDto requestDto = createTestRequestDto(testText);
        Message existingMessage = createTestMessage(messageId, true, testText);
        existingMessage.setImageAddress(existingImagePath);

        Message updatedMessage = createTestMessage(messageId, true, testText);
        updatedMessage.setImageAddress(existingImagePath);

        MessageResponseDto expectedResponse = new MessageResponseDto(
                messageId,
                1L,
                1L,
                existingImagePath,
                testText,
                testCreationTime
        );

        when(messageDao.findById(messageId)).thenReturn(Optional.of(existingMessage));
        when(messageMapper.fromDto(requestDto)).thenReturn(updatedMessage);
        when(messageDao.save(updatedMessage)).thenReturn(updatedMessage);
        when(messageMapper.toMessageResponse(updatedMessage)).thenReturn(expectedResponse);

        // Act
        MessageResponseDto actualResponse = messageService.updateWithImage(requestDto, messageId, null);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(existingImagePath, actualResponse.imageAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void add_ShouldReturnNull_WhenExceptionThrown() {
        // Arrange
        MessageCreateRequestDto requestDto = createTestRequestDto("Test");
        when(messageMapper.fromDto(requestDto)).thenThrow(new RuntimeException("Test exception"));

        // Act
        MessageResponseDto response = messageService.add(requestDto);

        // Assert
        assertNull(response);
    }

    @Test
    void get_ShouldReturnEmptyOptional_WhenMessageNotExists() {
        // Arrange
        Long messageId = 999L;
        when(messageDao.findById(messageId)).thenReturn(Optional.empty());

        // Act
        Optional<MessageResponseDto> response = messageService.get(messageId);

        // Assert
        assertTrue(response.isEmpty());
    }
}
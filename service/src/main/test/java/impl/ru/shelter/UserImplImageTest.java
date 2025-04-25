package impl.ru.shelter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.impl.ImageStorageImpl;
import ru.shelter.impl.UserImpl;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserImplImageTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ImageStorageImpl imageStorage;

    @InjectMocks
    private UserImpl userService;

    private final LocalDate testBirthday = LocalDate.of(1990, 1, 1);
    private final String encodedPassword = "encodedPassword123";
    private final String testImagePath = "/uploads/test_image.jpg";

    @Test
    void addWithImage_ShouldReturnUserResponseWithImagePath_WhenSuccess() {
        // Arrange
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test User",
                testBirthday,
                "test@example.com",
                "+1234567890",
                "password123"
        );

        MultipartFile image = new MockMultipartFile(
                "image",
                "test_image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        User user = new User();
        user.setName(requestDto.name());
        user.setBirthdayDate(requestDto.birthdayDate());
        user.setEmail(requestDto.email());
        user.setPhoneNumber(requestDto.phoneNumber());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(user.getName());
        savedUser.setBirthdayDate(user.getBirthdayDate());
        savedUser.setEmail(user.getEmail());
        savedUser.setPhoneNumber(user.getPhoneNumber());
        savedUser.setProfilePicAddress(testImagePath);

        UserResponseDto expectedResponse = new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getBirthdayDate(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getProfilePicAddress(),
                savedUser.getRegisterTime()
        );

        when(userMapper.fromDto(requestDto)).thenReturn(user);
        when(passwordEncoder.encode(requestDto.password())).thenReturn(encodedPassword);
        when(imageStorage.saveImage(image)).thenReturn(testImagePath);
        when(userDao.save(user)).thenReturn(savedUser);
        when(userMapper.toUserResponse(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponseDto actualResponse = userService.addWithImage(requestDto, image);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.name(), actualResponse.name());
        assertEquals(expectedResponse.email(), actualResponse.email());
        verify(passwordEncoder).encode(requestDto.password());
        verify(imageStorage).saveImage(image);
        verify(userDao).save(user);
        assertEquals(testImagePath, savedUser.getProfilePicAddress());
    }

    @Test
    void addWithImage_ShouldReturnNull_WhenImageSavingFails() {
        // Arrange
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test User",
                testBirthday,
                "test@example.com",
                "+1234567890",
                "password123"
        );

        MultipartFile image = new MockMultipartFile(
                "image",
                "test_image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        when(userMapper.fromDto(requestDto)).thenReturn(new User());
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(imageStorage.saveImage(image)).thenThrow(new RuntimeException("Failed to save image"));

        // Act
        UserResponseDto response = userService.addWithImage(requestDto, image);

        // Assert
        assertNull(response);
        verify(imageStorage).saveImage(image);
        verify(userDao, never()).save(any());
    }

    @Test
    void addWithImage_ShouldReturnNull_WhenUserSavingFails() {
        // Arrange
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test User",
                testBirthday,
                "test@example.com",
                "+1234567890",
                "password123"
        );

        MultipartFile image = new MockMultipartFile(
                "image",
                "test_image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        User user = new User();
        when(userMapper.fromDto(requestDto)).thenReturn(user);
        when(passwordEncoder.encode(requestDto.password())).thenReturn(encodedPassword);
        when(imageStorage.saveImage(image)).thenReturn(testImagePath);
        when(userDao.save(user)).thenThrow(new RuntimeException("Database error"));

        // Act
        UserResponseDto response = userService.addWithImage(requestDto, image);

        // Assert
        assertNull(response);
        verify(userDao).save(user);
    }

    @Test
    void addWithImage_ShouldWork_WhenImageIsNull() {
        // Arrange
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test User",
                testBirthday,
                "test@example.com",
                "+1234567890",
                "password123"
        );

        User user = new User();
        user.setName(requestDto.name());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(user.getName());

        UserResponseDto expectedResponse = new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getBirthdayDate(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getProfilePicAddress(),
                savedUser.getRegisterTime()
        );

        when(userMapper.fromDto(requestDto)).thenReturn(user);
        when(passwordEncoder.encode(requestDto.password())).thenReturn(encodedPassword);
        when(userDao.save(user)).thenReturn(savedUser);
        when(userMapper.toUserResponse(savedUser)).thenReturn(expectedResponse);

        // Act
        UserResponseDto actualResponse = userService.addWithImage(requestDto, null);
        System.out.println(actualResponse);
        System.out.println(requestDto);
        // Assert
        assertNotNull(actualResponse);
        assertNull(savedUser.getProfilePicAddress());
        verify(imageStorage, never()).saveImage(any());
    }
}
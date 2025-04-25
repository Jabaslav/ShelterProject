package ru.shelter.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.interfaces.UserDao;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserImpl userService;

    private final LocalDate testBirthday = LocalDate.of(1990, 1, 1);
    private final String encodedPassword = "encodedPassword123";

    @Test
    void add_ShouldReturnUserResponse_WhenValidRequest() {
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
        user.setBirthdayDate(requestDto.birthdayDate());
        user.setEmail(requestDto.email());
        user.setPhoneNumber(requestDto.phoneNumber());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(user.getName());
        savedUser.setBirthdayDate(user.getBirthdayDate());
        savedUser.setEmail(user.getEmail());
        savedUser.setPhoneNumber(user.getPhoneNumber());

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
        UserResponseDto actualResponse = userService.add(requestDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.name(), actualResponse.name());
        assertEquals(expectedResponse.email(), actualResponse.email());
        verify(passwordEncoder).encode(requestDto.password());
        verify(userDao).save(user);
    }

    @Test
    void add_ShouldReturnNull_WhenExceptionThrown() {
        // Arrange
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test User",
                testBirthday,
                "test@example.com",
                "+1234567890",
                "password123"
        );

        when(userMapper.fromDto(requestDto)).thenThrow(new RuntimeException("Test exception"));

        // Act
        UserResponseDto response = userService.add(requestDto);

        // Assert
        assertNull(response);
    }

    @Test
    void get_ShouldReturnUserResponse_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setBirthdayDate(testBirthday);
        user.setEmail("test@example.com");
        user.setPhoneNumber("+1234567890");

        UserResponseDto expectedResponse = new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getBirthdayDate(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProfilePicAddress(),
                user.getRegisterTime()
        );

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(expectedResponse);

        // Act
        Optional<UserResponseDto> actualResponse = userService.get(userId);

        // Assert
        assertTrue(actualResponse.isPresent());
        assertEquals(expectedResponse, actualResponse.get());
    }

    @Test
    void get_ShouldReturnEmptyOptional_WhenUserNotExists() {
        // Arrange
        Long userId = 999L;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<UserResponseDto> response = userService.get(userId);

        // Assert
        assertTrue(response.isEmpty());
    }

    @Test
    void get_ShouldReturnEmptyOptional_WhenExceptionThrown() {
        // Arrange
        Long userId = 1L;
        when(userDao.findById(userId)).thenThrow(new RuntimeException("Test exception"));

        // Act
        Optional<UserResponseDto> response = userService.get(userId);

        // Assert
        assertTrue(response.isEmpty());
    }

    @Test
    void update_ShouldReturnUpdatedUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Updated User",
                testBirthday,
                "updated@example.com",
                "+9876543210",
                "newpassword"
        );

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");
        existingUser.setBirthdayDate(LocalDate.of(1980, 1, 1));
        existingUser.setEmail("old@example.com");
        existingUser.setPhoneNumber("+1111111111");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(requestDto.name());
        updatedUser.setBirthdayDate(requestDto.birthdayDate());
        updatedUser.setEmail(requestDto.email());
        updatedUser.setPhoneNumber(requestDto.phoneNumber());

        UserResponseDto expectedResponse = new UserResponseDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getBirthdayDate(),
                updatedUser.getEmail(),
                updatedUser.getPhoneNumber(),
                updatedUser.getProfilePicAddress(),
                updatedUser.getRegisterTime()
        );

        when(userDao.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.fromDto(requestDto)).thenReturn(updatedUser);
        when(userDao.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.toUserResponse(updatedUser)).thenReturn(expectedResponse);

        // Act
        UserResponseDto actualResponse = userService.update(requestDto, userId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.name(), actualResponse.name());
        assertEquals(expectedResponse.email(), actualResponse.email());
        verify(userDao).save(updatedUser);
    }

    @Test
    void update_ShouldReturnNull_WhenUserNotExists() {
        // Arrange
        Long userId = 999L;
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Updated User",
                testBirthday,
                "updated@example.com",
                "+9876543210",
                "newpassword"
        );

        when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertNull(userService.update(requestDto, userId));
    }

    @Test
    void update_ShouldReturnNull_WhenExceptionThrown() {
        // Arrange
        Long userId = 1L;
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Updated User",
                testBirthday,
                "updated@example.com",
                "+9876543210",
                "newpassword"
        );

        when(userDao.findById(userId)).thenThrow(new RuntimeException("Test exception"));

        // Act
        UserResponseDto response = userService.update(requestDto, userId);

        // Assert
        assertNull(response);
    }

    @Test
    void remove_ShouldReturnTrue_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userDao).deleteById(userId);

        // Act
        boolean result = userService.remove(userId);

        // Assert
        assertTrue(result);
        verify(userDao).deleteById(userId);
    }

    @Test
    void remove_ShouldReturnFalse_WhenExceptionThrown() {
        // Arrange
        Long userId = 1L;
        doThrow(new RuntimeException("Test exception")).when(userDao).deleteById(userId);

        // Act
        boolean result = userService.remove(userId);

        // Assert
        assertFalse(result);
    }

    @Test
    void getAll_ShouldReturnListOfUsers_WhenUsersExist() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");
        user2.setEmail("user2@example.com");

        List<User> users = List.of(user1, user2);

        UserResponseDto responseDto1 = new UserResponseDto(
                user1.getId(),
                user1.getName(),
                user1.getBirthdayDate(),
                user1.getEmail(),
                user1.getPhoneNumber(),
                user1.getProfilePicAddress(),
                user1.getRegisterTime()
        );

        UserResponseDto responseDto2 = new UserResponseDto(
                user2.getId(),
                user2.getName(),
                user2.getBirthdayDate(),
                user2.getEmail(),
                user2.getPhoneNumber(),
                user2.getProfilePicAddress(),
                user2.getRegisterTime()
        );

        List<UserResponseDto> expectedResponse = List.of(responseDto1, responseDto2);

        when(userDao.findAll()).thenReturn(users);
        when(userMapper.toUserResponseList(users)).thenReturn(expectedResponse);

        // Act
        List<UserResponseDto> actualResponse = userService.getAll();

        // Assert
        assertEquals(2, actualResponse.size());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoUsersExist() {
        // Arrange
        when(userDao.findAll()).thenReturn(Collections.emptyList());
        when(userMapper.toUserResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        // Act
        List<UserResponseDto> response = userService.getAll();

        // Assert
        assertTrue(response.isEmpty());
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenExceptionThrown() {
        // Arrange
        when(userDao.findAll()).thenThrow(new RuntimeException("Test exception"));

        // Act
        List<UserResponseDto> response = userService.getAll();

        // Assert
        assertTrue(response.isEmpty());
    }
}
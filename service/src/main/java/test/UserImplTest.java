package test;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shelter.dto.request.UserCreateRequestDto;
import ru.shelter.dto.response.UserResponseDto;
import ru.shelter.impl.UserImpl;
import ru.shelter.mapper.UserMapper;
import ru.shelter.model.User;
import ru.shelter.interfaces.UserDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Test
    void add_ValidUser_ReturnsUserResponseDto() {
        // Arrange
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test User",
                LocalDate.of(1990, 1, 1),
                "test@example.com",
                "+123456789",
                "password123"
        );

        User user = new User();
        user.setId(1L);
        user.setName(requestDto.name());
        user.setEmail(requestDto.email());

        UserResponseDto responseDto = new UserResponseDto(
                "Test User",
                LocalDate.of(1990, 1, 1),
                "test@example.com",
                "+123456789"
        );

        when(userMapper.fromDto(requestDto)).thenReturn(user);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userDao.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(responseDto);

        // Act
        UserResponseDto result = userService.add(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.userEmail());
        verify(passwordEncoder).encode("password123");
        verify(userDao).save(user);
    }

    @Test
    void get_ExistingUserId_ReturnsUserResponseDto() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        UserResponseDto responseDto = new UserResponseDto(
                "Test User",
                LocalDate.now(),
                "test@example.com",
                "+123456789"
        );

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toUserResponse(user)).thenReturn(responseDto);

        // Act
        Optional<UserResponseDto> result = userService.get(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().userEmail());
    }

    @Test
    void get_NonExistingUserId_ReturnsEmptyOptional() {
        // Arrange
        Long userId = 999L;
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<UserResponseDto> result = userService.get(userId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void update_ExistingUser_ReturnsUpdatedUserResponseDto() {
        // Arrange
        Long userId = 1L;
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Updated Name",
                LocalDate.of(1995, 5, 5),
                "updated@example.com",
                "+987654321",
                "newPassword"
        );

        User existingUser = new User();
        existingUser.setId(userId);

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(requestDto.name());

        UserResponseDto responseDto = new UserResponseDto(
                "Updated Name",
                LocalDate.of(1995, 5, 5),
                "updated@example.com",
                "+987654321"
        );

        when(userDao.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.fromDto(requestDto)).thenReturn(updatedUser);
        when(userDao.save(updatedUser)).thenReturn(updatedUser);
        when(userMapper.toUserResponse(updatedUser)).thenReturn(responseDto);

        // Act
        UserResponseDto result = userService.update(requestDto, userId);

        // Assert
        assertEquals("updated@example.com", result.userEmail());
    }

    @Test
    void update_NonExistingUser_ThrowsEntityNotFoundException() {
        // Arrange
        Long userId = 999L;
        UserCreateRequestDto requestDto = new UserCreateRequestDto(
                "Test",
                LocalDate.now(),
                "test@example.com",
                "+123456789",
                "password"
        );

        when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.update(requestDto, userId));
    }

    @Test
    void remove_ExistingUser_ReturnsTrue() {
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
    void remove_NonExistingUser_ReturnsFalse() {
        // Arrange
        Long userId = 999L;
        doThrow(new RuntimeException()).when(userDao).deleteById(userId);

        // Act
        boolean result = userService.remove(userId);

        // Assert
        assertFalse(result);
    }

    @Test
    void getAll_UsersExist_ReturnsUserList() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        List<User> users = List.of(user1, user2);

        UserResponseDto dto1 = new UserResponseDto("User1", LocalDate.now(), "user1@example.com", "+111111");
        UserResponseDto dto2 = new UserResponseDto("User2", LocalDate.now(), "user2@example.com", "+222222");

        when(userDao.findAll()).thenReturn(users);
        when(userMapper.toUserResponseList(users)).thenReturn(List.of(dto1, dto2));

        // Act
        List<UserResponseDto> result = userService.getAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getAll_DatabaseError_ReturnsEmptyList() {
        // Arrange
        when(userDao.findAll()).thenThrow(new RuntimeException());

        // Act
        List<UserResponseDto> result = userService.getAll();

        // Assert
        assertTrue(result.isEmpty());
    }
}


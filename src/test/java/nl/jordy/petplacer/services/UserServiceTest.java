package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private User getTestUser() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@gmail.com");
        user.setFirstName("dummy");
        user.setLastName("tester");
        return user;
    }

    @Mock
    UserRepository userRepository;

    @Mock
    AccessValidator accessValidator;

    @InjectMocks
    UserService userService;

    @DisplayName("Fetch user by username")
    @Test
    void fetchUserByUsername() {
        // Arrange
        User user = getTestUser();
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        // Act
        User result = userService.fetchUserByUsername("test");

        // Assert
        assertEquals(user, result);
    }

    @DisplayName("Throws a RecordNotFoundException when user is not found")
    @Test
    void fetchUserByUsernameNotFound() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            userService.fetchUserByUsername("test");
        });
    }

    @DisplayName("Throws a AlreadyExistsException when username is not unique")
    @Test
    void validateUserUnique() {
        UserInputDTO userDTO = new UserInputDTO();
        userDTO.setUsername("existingUser");


        when(userRepository.existsByUsernameIgnoreCase("existingUser")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> {
            userService.validateUserUnique(userDTO);
        });
    }

    @Test
    void registerUser() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findUsersByParams() {
    }

    @Test
    void updateUserByUsername() {
    }

    @Test
    void deleteUserByID() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void promoteToAdmin() {
    }

    @Test
    void demoteAdmin() {
    }
}
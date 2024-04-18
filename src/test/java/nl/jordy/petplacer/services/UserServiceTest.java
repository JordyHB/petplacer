package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.dtos.patch.UserPatchDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.interfaces.AuthorityChecker;
import nl.jordy.petplacer.models.Authority;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.UserRepository;
import nl.jordy.petplacer.specifications.UserSpecification;
import nl.jordy.petplacer.util.AccessValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private User getTestUser(String name) {
        User user = new User();
        user.setUsername(name);
        user.setEmail("test@gmail.com");
        user.setFirstName("dummy");
        user.setLastName("tester");
        return user;
    }

    @Mock
    UserRepository userRepository;

    @Mock
    AccessValidator accessValidator;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthorityChecker authChecker;

    @InjectMocks
    UserService userService;

    @DisplayName("Fetch user by username")
    @Test
    void fetchUserByUsername() {
        // Arrange
        User user = getTestUser("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        // Act
        User result = userService.fetchUserByUsername("test");

        // Assert
        assertEquals(user, result);
    }

    @DisplayName("Throws a RecordNotFoundException when user is not found")
    @Test
    void fetchUserByUsernameNotFound() {
        // Arrange
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> userService.fetchUserByUsername("test"));
    }

    @DisplayName("Throws a AlreadyExistsException when username is not unique")
    @Test
    void validateUserUnique() {
        // Arrange
        UserInputDTO userDTO = new UserInputDTO();
        userDTO.setUsername("existingUser");

        when(userRepository.existsByUsernameIgnoreCase("existingUser")).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> userService.validateUserUnique(userDTO));
    }

    @DisplayName("Throws a AlreadyExistsException when email is not unique")
    @Test
    void validateUserUniqueEmail() {
        // Arrange
        UserInputDTO userDTO = new UserInputDTO();
        userDTO.setEmail("existing@email.com");

        when(userRepository.existsByEmailIgnoreCase("existing@email.com")).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> userService.validateUserUnique(userDTO));
    }

    @DisplayName(("passes if the user is unique"))
    @Test
    void validateUserUniquePass() {
        // Arrange
        UserInputDTO userDTO = new UserInputDTO();
        userDTO.setUsername("newUser");
        userDTO.setEmail("new@email.com");

        when(userRepository.existsByUsernameIgnoreCase("newUser")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("new@email.com")).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> userService.validateUserUnique(userDTO));
    }

    @DisplayName("Registers a new user")
    @Test
    void registerUser() {
        // Arrange
        UserInputDTO userDTO = new UserInputDTO();
        userDTO.setUsername("test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setFirstName("dummy");
        userDTO.setLastName("tester");
        userDTO.setPassword("password");
        User mappedUser = getTestUser("test");

        when(userRepository.save(any(User.class))).thenReturn(mappedUser);

        // Act
        UserOutputDTO result = userService.registerUser(userDTO);

        // Assert
        assertEquals(userDTO.getUsername(), result.getUsername());
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(userDTO.getFirstName(), result.getFirstName());
        verify(passwordEncoder).encode(userDTO.getPassword());
    }

    @DisplayName("Find all users")
    @Test
    void findAllUsers() {
        // Arrange
        User user1 = getTestUser("test1");
        User user2 = getTestUser("test2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserOutputDTO> result = userService.findAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals(user1.getUsername(), result.get(0).getUsername());
        assertEquals(user2.getUsername(), result.get(1).getUsername());

    }

    @DisplayName("Find user by username success")
    @Test
    void findUserByUsername_success() {
        // Arrange
        User user = getTestUser("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        // Act
        UserOutputDTO result = userService.findUserByUsername("test");

        // Assert
        assertEquals(user.getUsername(), result.getUsername());
    }

    @DisplayName("Find user by username throws CustomAccessDeniedException when unauthorized")
    @Test
    void findUserByUsername_Unauthorized() {
        // Arrange
        doThrow(CustomAccessDeniedException.class).when(accessValidator).isUserOrAdmin(any(), anyString());

        // Act & Assert
        assertThrows(CustomAccessDeniedException.class, () -> userService.findUserByUsername("test"));
    }

    @DisplayName("Find users by params")
    @Test
    void findUsersByParams() {
        // Arrange
        User user1 = getTestUser("test1");
        User user2 = getTestUser("test2");
        List<User> users = List.of(user1, user2);

        when(userRepository.findAll(any(UserSpecification.class))).thenReturn(users);

        // Act
        List<UserOutputDTO> result = userService
                .findUsersByParams(null, "dummy", "tester", null);

        // Assert
        assertEquals(2, result.size());
        assertEquals(user1.getUsername(), result.get(0).getUsername());
        assertEquals(user2.getLastName(), result.get(1).getLastName());
    }

    @DisplayName("Update user by username")
    @Test
    void updateUserByUsername() {
        // Arrange
        String username = "test";
        User user = getTestUser("test");
        User updatedUser = getTestUser("test");
        updatedUser.setFirstName("newName");
        UserPatchDTO userPatchDTO = new UserPatchDTO();
        userPatchDTO.setFirstName("newName");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
         UserOutputDTO result = userService.updateUserByUsername(username, userPatchDTO);

        // Assert
        assertEquals("test", result.getUsername());
        assertEquals(updatedUser.getFirstName().toLowerCase(), result.getFirstName());
        verify(accessValidator).isUserOrAdmin(any(), eq(username));
    }

    @DisplayName("Delete user by username")
    @Test
    void deleteUserByUsername() {
        // Arrange
        String username = "test";
        User user = getTestUser("test");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUserByUsername(username);

        // Assert
        verify(userRepository).delete(user);
        verify(accessValidator).isUserOrAdmin(any(), eq(username));

    }

    @DisplayName("User service save user working")
    @Test
    void saveUser() {
        // Arrange
        User mockUser = mock(User.class);

        // Act
        userService.saveUser(mockUser);

        // Assert
        verify(userRepository).save(mockUser);
    }

    @DisplayName("Promote admin and helper tests")
    @Test
    void promoteToAdmin() {
        // Arrange
        String username = "test";
        User user = getTestUser("test");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserOutputDTO result = userService.promoteToAdmin(username);

        // Assert
        verify(userRepository).save(user);
        verify(authChecker).fetchedUserHasAuthority(user, "ROLE_ADMIN");
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

    }

    @DisplayName("Promote admin throws ex")
    @Test
    void promoteToAdminThrowsEX() {
        // Arrange
        String username = "test";
        User user = getTestUser("test");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(authChecker.fetchedUserHasAuthority(any(User.class), eq("ROLE_ADMIN"))).thenReturn(true);

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> userService.promoteToAdmin(username));
    }

    @DisplayName("Demote Admin test")
    @Test
    void demoteAdmin() {
        // Arrange
        String username = "test";
        User user = getTestUser("test");
        user.addAuthority(new Authority("test", "ROLE_ADMIN"));

        when(authChecker.fetchedUserHasAuthority(any(User.class), eq("ROLE_ADMIN"))).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserOutputDTO result = userService.demoteAdmin(username);

        // Assert
        verify(userRepository).save(user);
        verify(authChecker).fetchedUserHasAuthority(user, "ROLE_ADMIN");
        assertTrue(result.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

    }

    @DisplayName("Demote admin, throws exception")
    @Test
    void demoteAdminThrowsEx() {
        // Arrange
        String username = "test";
        User user = getTestUser("test");

        when(authChecker.fetchedUserHasAuthority(any(User.class), eq("ROLE_ADMIN"))).thenReturn(false);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(AlreadyExistsException.class, () -> userService.demoteAdmin(username));

    }
}
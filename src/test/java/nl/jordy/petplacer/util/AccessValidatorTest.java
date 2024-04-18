package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser(username = "admin", roles = {"ADMIN"})
@AutoConfigureMockMvc(addFilters = false)
class AccessValidatorTest {

    @Autowired
    private AccessValidator accessValidator;

    @DisplayName("Get the authentication")
    @Test
    void getAuth() {
        // Act
        Authentication authentication = accessValidator.getAuth();

        // Assert
        assertNotNull(authentication, "Authentication should not be null");
        assertEquals("admin", authentication.getName());
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    void isAdmin() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Act
        boolean result = accessValidator.isAdmin(authentication);

        // Assert
        assertTrue(result, "The user should be an admin");
    }

    @DisplayName("is user or admin, admin match")
    @Test
    void isUserOrAdmin_AdminMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String requestedUsername = "dirk";

        // Act
        accessValidator.isUserOrAdmin(authentication, requestedUsername);

        // Assert
        assertDoesNotThrow(() -> accessValidator.isUserOrAdmin(authentication, requestedUsername));
    }

    @WithMockUser(username = "dirk")
    @DisplayName("is user or admin, user match")
    @Test
    void isUserOrAdmin_UserMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String requestedUsername = "dirk";

        // Act
        accessValidator.isUserOrAdmin(authentication, requestedUsername);

        // Assert
        assertDoesNotThrow(() -> accessValidator.isUserOrAdmin(authentication, requestedUsername));
    }

    @WithMockUser(username = "dirk")
    @DisplayName("is user or admin, no match")
    @Test
    void isUserOrAdmin_NoMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String requestedUsername = "admin";

        // Act & Assert
        assertThrows(CustomAccessDeniedException.class,
                () -> accessValidator.isUserOrAdmin(authentication, requestedUsername));
    }

    @Test
    void isSheltersManagerOrAdminFilterOnly() {
    }

    @Test
    void isSheltersManagerOrAdmin() {
    }

    @Test
    void canAccessAdoptionInfo() {
    }
}
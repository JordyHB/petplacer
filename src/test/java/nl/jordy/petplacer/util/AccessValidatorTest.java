package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

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

    @DisplayName("is shelters manager or admin filter only, admin match")
    @Test
    void isSheltersManagerOrAdminFilterOnly() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();

        // Act
        boolean result = accessValidator.isSheltersManagerOrAdminFilterOnly(authentication, requestedShelter);

        // Assert
        assertTrue(result, "The user should be an admin");

    }

    @DisplayName("is shelters manager or admin filter only, manager match")
    @WithMockUser(username = "dirk", roles = {"SHELTER_MANAGER"})
    @Test
    void isSheltersManagerOrAdminFilterOnly_ManagerMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();
        User manager = new User();
        manager.setUsername("dirk");
        requestedShelter.setManagers(List.of(manager));

        // Act
        boolean result = accessValidator.isSheltersManagerOrAdminFilterOnly(authentication, requestedShelter);

        // Assert
        assertTrue(result, "The user should be a manager");
    }

    @DisplayName("is shelters manager or admin filter only, no match")
    @WithMockUser(username = "dirk")
    @Test
    void isSheltersManagerOrAdminFilterOnly_NoMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();
        User manager = new User();
        manager.setUsername("admin");
        requestedShelter.setManagers(List.of(manager));

        // Act
        boolean result = accessValidator.isSheltersManagerOrAdminFilterOnly(authentication, requestedShelter);

        // Assert
        assertFalse(result, "The user should not be a manager");
    }

    @Test
    void isSheltersManagerOrAdmin() {
    }

    @Test
    void canAccessAdoptionInfo() {
    }
}
package nl.jordy.petplacer.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
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

    }

    @Test
    void isUserOrAdmin() {
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
package nl.jordy.petplacer.util;

import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AccessValidator.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
@ActiveProfiles("test")
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

    private AdoptionRequest createMockAdoptionRequest(String managerUsername) {
        User manager = new User();
        manager.setUsername(managerUsername);
        User applicant = new User();
        applicant.setUsername("applicant");
        Shelter shelter = new Shelter();
        shelter.setManagers(List.of(manager));
        ShelterPet shelterPet = new ShelterPet();
        shelterPet.setShelter(shelter);
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        adoptionRequest.setAdoptionApplicant(applicant);
        adoptionRequest.setRequestedPet(shelterPet);
        return adoptionRequest;
    }

    @DisplayName("is admin")
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

    @DisplayName("is shelters manager or admin, admin match")
    @Test
    void isSheltersManagerOrAdmin_AdminMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();

        // Act
        accessValidator.isSheltersManagerOrAdmin(authentication, requestedShelter);

        // Assert
        assertDoesNotThrow(() -> accessValidator.isSheltersManagerOrAdmin(authentication, requestedShelter));
    }

    @DisplayName("is shelters manager or admin, manager match")
    @WithMockUser(username = "dirk", roles = {"SHELTER_MANAGER"})
    @Test
    void isSheltersManagerOrAdmin_ManagerMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();
        User manager = new User();
        manager.setUsername("dirk");
        requestedShelter.setManagers(List.of(manager));

        // Act
        accessValidator.isSheltersManagerOrAdmin(authentication, requestedShelter);

        // Assert
        assertDoesNotThrow(() -> accessValidator.isSheltersManagerOrAdmin(authentication, requestedShelter));
    }

    @DisplayName("is shelters manager or admin, shelter manager but not of the right shelter")
    @WithMockUser(username = "dirk", roles = {"SHELTER_MANAGER"})
    @Test
    void isSheltersManagerOrAdmin_NoMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();
        User manager = new User();
        manager.setUsername("admin");
        requestedShelter.setManagers(List.of(manager));

        // Act & Assert
        assertThrows(CustomAccessDeniedException.class,
                () -> accessValidator.isSheltersManagerOrAdmin(authentication, requestedShelter));
    }

    @DisplayName("is shelters manager or admin, no match, not a shelter manager")
    @WithMockUser(username = "dirk")
    @Test
    void isSheltersManagerOrAdmin_NoMatchNoShelterManager() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Shelter requestedShelter = new Shelter();
        User manager = new User();
        manager.setUsername("admin");
        requestedShelter.setManagers(List.of(manager));

        // Act & Assert
        assertThrows(CustomAccessDeniedException.class,
                () -> accessValidator.isSheltersManagerOrAdmin(authentication, requestedShelter));
    }

    @DisplayName("can access adoption info, manager match")
    @WithMockUser(username = "dirk", roles = {"SHELTER_MANAGER"})
    @Test
    void canAccessAdoptionInfo_ManagerMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdoptionRequest adoptionRequest = createMockAdoptionRequest("dirk");

        // Act
        boolean result = accessValidator.canAccessAdoptionInfo(authentication, adoptionRequest);

        // Assert
        assertTrue(result, "The user should be a manager");
    }

    @DisplayName("can access adoption info, applicant match")
    @WithMockUser(username = "applicant")
    @Test
    void canAccessAdoptionInfo_ApplicantMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdoptionRequest adoptionRequest =
                createMockAdoptionRequest("manager");

        // Act
        boolean result = accessValidator.canAccessAdoptionInfo(authentication, adoptionRequest);

        // Assert
        assertTrue(result, "The user should be an applicant");
    }

    @DisplayName("can access adoption info, admin match")
    @Test
    void canAccessAdoptionInfo_AdminMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdoptionRequest adoptionRequest =
                createMockAdoptionRequest("manager");

        // Act
        boolean result = accessValidator.canAccessAdoptionInfo(authentication, adoptionRequest);

        // Assert
        assertTrue(result, "The user should be an admin");
    }

    @DisplayName("can access adoption info, no match")
    @WithMockUser(username = "dirk")
    @Test
    void canAccessAdoptionInfo_NoMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdoptionRequest adoptionRequest =
                createMockAdoptionRequest("manager");

        // Act
        boolean result = accessValidator.canAccessAdoptionInfo(authentication, adoptionRequest);

        // Assert
        assertFalse(result, "The user should not be a manager, applicant or admin");
    }

    @DisplayName("can access donation info as admin")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void canAccessDonationInfoAsAdmin() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Donation donation = new Donation();
        User donator = new User();
        donator.setUsername("donator");
        donation.setDonator(donator);

        // Act
        boolean result = accessValidator.canAccessDonationInfo(authentication, donation);

        // Assert
        assertTrue(result, "The user should be an admin");
    }

    @DisplayName("can access donation info as donator")
    @WithMockUser(username = "donatorUsername")
    @Test
    void canAccessDonationInfoAsDonator() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Donation donation = new Donation();
        Shelter receivingShelter = new Shelter();
        User randomUser = new User();
        randomUser.setUsername("randomUser");
        receivingShelter.setManagers(List.of(randomUser));
        User donator = new User();
        donator.setUsername("donatorUsername");
        donation.setDonator(donator);
        donation.setReceivingShelter(receivingShelter);

        // Act
        boolean result = accessValidator.canAccessDonationInfo(authentication, donation);

        // Assert
        assertTrue(result, "The user should be the donator");
    }

    @DisplayName("can access donation info as shelter manager")
    @WithMockUser(username = "manager", roles = {"USER", "SHELTER_MANAGER"})
    @Test
    void canAccessDonationInfoAsShelterManager() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Donation donation = new Donation();
        Shelter receivingShelter = new Shelter();
        User manager = new User();
        manager.setUsername("manager");
        receivingShelter.setManagers(List.of(manager));
        User donator = new User();
        donator.setUsername("donator");
        donation.setDonator(donator);
        donation.setReceivingShelter(receivingShelter);

        // Act
        boolean result = accessValidator.canAccessDonationInfo(authentication, donation);

        // Assert
        assertTrue(result, "The user should be the shelter manager");
    }

    @DisplayName("can access donation info as shelter manager, no match")
    @WithMockUser(username = "different_manager", roles = {"USER", "SHELTER_MANAGER"})
    @Test
    void canAccessDonationInfoAsShelterManagerNoMatch() {
        // Arrange
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Donation donation = new Donation();
        Shelter receivingShelter = new Shelter();
        User manager = new User();
        manager.setUsername("manager");
        receivingShelter.setManagers(List.of(manager));
        User donator = new User();
        donator.setUsername("donator");
        donation.setDonator(donator);
        donation.setReceivingShelter(receivingShelter);

        // Act
        boolean result = accessValidator.canAccessDonationInfo(authentication, donation);

        // Assert
        assertFalse(result, "The user is not a manager for this shelter.");
    }
}
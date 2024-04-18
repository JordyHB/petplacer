package nl.jordy.petplacer.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShelterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("successfully add a shelterpet as manager")
    @WithMockUser(username = "jord", roles = {"USER", "SHELTER_MANAGER"})
    @Test
    void registerShelterPet() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                        "name": "Buddy",
                        "species": "Dog",
                        "breed": "Labrador Retriever",
                        "color": "Golden",
                        "age": 3,
                        "gender": "MALE",
                        "size": "Large",
                         "description": "Friendly and energetic",
                        "spayedNeutered": true,
                        "goodWithKids": true,
                        "goodWithDogs": true,
                        "goodWithCats": false,
                        "monthsInShelter": 2,
                        "medicalHistory": "Vaccinated and tested",
                        "specialNeeds": "",
                        "previousSituation": "Rescued from the streets"
                        }
                               """;

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/shelters/1/shelterpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("buddy"))
                .andExpect(jsonPath("$.dateOfLastUpdate").exists())
                .andExpect(jsonPath("$.shelter.id").value("1"));
    }

    @DisplayName("successfully add a shelterpet as admin")
    @WithMockUser(username = "randomadmin", roles = {"USER", "ADMIN"})
    @Test
    void registerShelterPetAsAdmin() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                        "name": "Buddy",
                        "species": "Dog",
                        "breed": "Labrador Retriever",
                        "color": "Golden",
                        "age": 3,
                        "gender": "MALE",
                        "size": "Large",
                         "description": "Friendly and energetic",
                        "spayedNeutered": true,
                        "goodWithKids": true,
                        "goodWithDogs": true,
                        "goodWithCats": false,
                        "monthsInShelter": 2,
                        "medicalHistory": "Vaccinated and tested",
                        "specialNeeds": "",
                        "previousSituation": "Rescued from the streets"
                        }
                               """;

// Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/shelters/1/shelterpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("buddy"))
                .andExpect(jsonPath("$.dateOfLastUpdate").exists())
                .andExpect(jsonPath("$.shelter.id").value("1"));
    }

@DisplayName("fail to add a shelterpet as unauthorized user")
@WithMockUser(username = "randomuser", roles = {"USER", "SHELTER_MANAGER"})
@Test
void registerShelterPetAsUnauthorizedUser() throws Exception {
// Arrange
    String requestJson =
            """
                    {
                    "name": "Buddy",
                    "species": "Dog",
                    "breed": "Labrador Retriever",
                    "color": "Golden",
                    "age": 3,
                    "gender": "MALE",
                    "size": "Large",
                     "description": "Friendly and energetic",
                    "spayedNeutered": true,
                    "goodWithKids": true,
                    "goodWithDogs": true,
                    "goodWithCats": false,
                    "monthsInShelter": 2,
                    "medicalHistory": "Vaccinated and tested",
                    "specialNeeds": "",
                    "previousSituation": "Rescued from the streets"
                    }
                           """;

    // Act & Assert
    this.mockMvc.perform(MockMvcRequestBuilders.post("/shelters/1/shelterpets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isUnauthorized());
}

    @DisplayName("successfully add a donation")
    @WithMockUser(username = "jord")
    @Test
    void addDonation() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                        "donationAmount": 100.00,
                        "donationMessage": "Thank you for the great work!"
                        }
                               """;

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/shelters/1/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.donationAmount").value(100.00))
                .andExpect(jsonPath("$.donationMessage").value("thank you for the great work!"))
                .andExpect(jsonPath("$.dateOfDonation").exists());
    }

    @DisplayName("successfully get all shelters")
    @Test
    void getAllShelters() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shelters"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @DisplayName("successfully get shelter by ID")
    @Test
    void getShelterByID() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shelters/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getShelterPetsByShelterID() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shelters/1/shelterpets"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getSheltersByParams() {
    }

    @Test
    void addManagerToShelter() {
    }

    @Test
    void updateShelterByID() {
    }

    @Test
    void deleteShelterByID() {
    }

    @Test
    void removeManagerFromShelter() {
    }
}
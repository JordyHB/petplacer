package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.services.JwtService;
import nl.jordy.petplacer.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @DisplayName("Register a user successfully")
    @Test
    void registerUser() throws Exception {
        // Arrange
        String requestJson = """
                {
                "username": "Emel",
                "password": "5678",
                "firstName": "Emel",
                "lastName": "OzdaFinland",
                "email": "Emel@example.com",
                "phoneNumber": "0656464462"
                }
                """;

        // Act & Assert
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("emel"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andReturn();

        // Assert
        assertThat(mvcResult.getResponse().getHeader("Location"),
                matchesPattern("^.*/users/emel$"));
    }

    @DisplayName("register a user with an existing username")
    @Test
    void registerUserWithExistingUsername() throws Exception {
        // Arrange
        String requestJson = """
                {
                "username": "jord",
                "password": "5678",
                "firstName": "jordy",
                "lastName": "doe",
                "email": "jord@example.com",
                "phoneNumber": "0656464462"
                }
                """;

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }


    @DisplayName("Register a shelter successfully")
    @Test
    void registerShelter() throws Exception {

        // Arrange
        String requestJson =
                """
                        {
                                "shelterName":"Example Shelter",
                                "phoneNumber":"0612345678",
                                "email":"example@example.com",
                                "address":"123 Example Street",
                                "city":"Example City",
                                "postalCode":"1234AB",
                                "description":"This is a description of the shelter.",
                                "website":"https://www.example.com",
                                "facilities":"Facility 1, Facility 2, Facility 3",
                                "openingHours":"Monday to Friday: 9am to 5pm"
                        }
                                """;

        // Act & Assert
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/users/jord/shelters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shelterName").value("example shelter"))
                .andExpect(jsonPath("$.dateOfRegistration").exists())
                .andExpect(jsonPath("$.managers[0].username").value("jord"))
                .andReturn();

    }

    @DisplayName("Register a shelter with an existing name")
    @Test
    void registerShelterWithExistingName() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                         "shelterName":"animal shelter the farm",
                         "phoneNumber":"0612345678",
                         "email":"example@example.com",
                         "address":"123 Example Street",
                         "city":"Example City",
                         "postalCode":"1234AB",
                         "description":"This is a description of the shelter.",
                         "website":"https://www.example.com",
                         "facilities":"Facility 1, Facility 2, Facility 3",
                         "openingHours":"Monday to Friday: 9am to 5pm"
                         }
                         """;

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/jord/shelters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @DisplayName("Register a shelter with a non-existing user")
    @Test
    void registerShelterWithNonExistingUser() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                         "shelterName":"animal shelter the city",
                         "phoneNumber":"0612345678",
                         "email":"example@example.com",
                         "address":"123 Example Street",
                         "city":"Example City",
                         "postalCode":"1234AB",
                         "description":"This is a description of the shelter.",
                         "website":"https://www.example.com",
                         "facilities":"Facility 1, Facility 2, Facility 3",
                         "openingHours":"Monday to Friday: 9am to 5pm"
                         }
                         """;

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/nonexistinguser/shelters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }


    @Test
    void registerUserOwnedPet() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserByID() {
    }

    @Test
    void getUsersByParams() {
    }

    @Test
    void promoteToAdmin() {
    }

    @Test
    void updateUserByID() {
    }

    @Test
    void deleteUserByUsername() {
    }

    @Test
    void demoteAdmin() {
    }
}
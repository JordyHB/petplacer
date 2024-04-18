package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.services.JwtService;
import nl.jordy.petplacer.services.UserService;
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

    @Test
    void registerShelter() {
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
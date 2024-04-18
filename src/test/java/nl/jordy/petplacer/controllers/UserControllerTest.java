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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerTest {

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;


    @Test
    void registerUser() {
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
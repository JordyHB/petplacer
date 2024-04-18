package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.services.JwtService;
import nl.jordy.petplacer.services.ShelterService;
import nl.jordy.petplacer.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
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
    @WithMockUser(username = "jord")
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
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/jord/shelters")
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
    @WithMockUser(username = "randomuser")
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

    @DisplayName("Register a shelter with to a non-existing user")
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    @Test
    void registerShelterToANonExistingUser() throws Exception {
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


    @DisplayName("Register a user owned pet successfully")
    @WithMockUser(username = "jord")
    @Test
    void registerUserOwnedPet() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                          "name": "Fluffy",
                          "species": "Cat",
                          "breed": "Maine Coon",
                          "color": "Brown Tabby",
                          "age": 3,
                          "gender": "FEMALE",
                          "size": "Medium",
                          "description": "Fluffy loves belly rubs and playing with yarn",
                          "spayedNeutered": true,
                          "goodWithKids": true,
                          "goodWithDogs": true,
                          "goodWithCats": false,
                          "isAdopted": false,
                          "yearsOwned": 2
                        }
                        """;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/jord/ownedpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size").value("medium"))
                .andExpect(jsonPath("$.currentOwner.username").value("jord"));
    }

    @DisplayName("Register a user owned pet without being the owner")
    @WithMockUser(username = "nonowner")
    @Test
    void registerUserOwnedPetWithoutBeingTheOwner() throws Exception {
        String requestJson =
                """
                        {
                          "name": "Fluffy",
                          "species": "Cat",
                          "breed": "Maine Coon",
                          "color": "Brown Tabby",
                          "age": 3,
                          "gender": "FEMALE",
                          "size": "Medium",
                          "description": "Fluffy loves belly rubs and playing with yarn",
                          "spayedNeutered": true,
                          "goodWithKids": true,
                          "goodWithDogs": true,
                          "goodWithCats": false,
                          "isAdopted": false,
                          "yearsOwned": 2
                        }
                        """;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/jord/ownedpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Register a user owned pet successfully")
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    @Test
    void registerUserOwnedPetAsAdmin() throws Exception {
        // Arrange
        String requestJson =
                """
                        {
                          "name": "Fluffy",
                          "species": "Cat",
                          "breed": "Maine Coon",
                          "color": "Brown Tabby",
                          "age": 3,
                          "gender": "FEMALE",
                          "size": "Medium",
                          "description": "Fluffy loves belly rubs and playing with yarn",
                          "spayedNeutered": true,
                          "goodWithKids": true,
                          "goodWithDogs": true,
                          "goodWithCats": false,
                          "isAdopted": false,
                          "yearsOwned": 2
                        }
                        """;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/jord/ownedpets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size").value("medium"))
                .andExpect(jsonPath("$.currentOwner.username").value("jord"));
    }

    @DisplayName("Get all users as an admin")
    @WithMockUser(username = "Admin", roles = "ADMIN")
    @Test
    void getAllUsers() throws Exception {

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("Get all users as a user")
    @WithMockUser(username = "randomuser", roles = "USER")
    @Test
    void getAllUsersAsUser() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("Get a user by username successfully")
    @WithMockUser(username = "jord")
    @Test
    void getUserByUsername() throws Exception {

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/jord"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jord"))
                .andExpect(jsonPath("$.lastName").value("doe"));
    }

    @DisplayName("Get a user by username successfully as an admin")
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    @Test
    void getUserByUsernameAsAdmin() throws Exception {

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/jord"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jord"))
                .andExpect(jsonPath("$.lastName").value("doe"));
    }

    @DisplayName("Get a user by username unsuccessfully as incorrect user")
    @WithMockUser(username = "randomuser")
    @Test
    void getUserByUsernameAsIncorrectUser() throws Exception {

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/jord"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Filter through users as an admin")
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    @Test
    void getUsersByParams() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/filter?username=jor"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].username").exists());
    }

    @DisplayName("promote a user to admin as an admin")
    @WithMockUser(username = "Admin", roles = {"USER", "ADMIN"})
    @Test
    void promoteToAdmin() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/jord/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jord"))
                .andExpect(jsonPath("$.authorities[*].authority").value(hasItem("ROLE_ADMIN")));
    }

    @DisplayName("promote a user to admin as a user")
    @WithMockUser(username = "randomuser")
    @Test
    void promoteToAdminAsUser() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/jord/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("update a user by username")
    @WithMockUser(username = "jord")
    @Test
    void updateUserByUsername() throws Exception {
        // Arrange
        String requestJson = """
                {
                "firstName": "updatedfirstname",
                "lastName": "updatedlastname"
                }
                """;

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/users/jord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("updatedfirstname"))
                .andExpect(jsonPath("$.lastName").value("updatedlastname"));
    }

    @DisplayName("Delete a user by username")
    @WithMockUser(username = "msbear")
    @Test
    void deleteUserByUsername() throws Exception {
        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/msbear"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @DisplayName("Demote an admin")
    @WithMockUser(username = "freek", roles = {"USER", "ADMIN"})
    @Test
    void demoteAdmin() throws Exception {

        // Act & Assert
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/admin/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.authorities[*].authority").value(hasItem("ROLE_USER")))
                .andExpect(jsonPath("$.authorities[*].authority").value(not(hasItem("ROLE_ADMIN"))));
    }
}
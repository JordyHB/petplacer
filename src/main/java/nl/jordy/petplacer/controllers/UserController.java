package nl.jordy.petplacer.controllers;


import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.ShelterInputDTO;
import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterOutputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.dtos.patch.UserPatchDTO;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.services.ShelterService;
import nl.jordy.petplacer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ShelterService shelterService;

    public UserController(UserService userService, ShelterService shelterService) {

        this.userService = userService;
        this.shelterService = shelterService;
    }


    // Posts
    @PostMapping()
    public ResponseEntity<UserOutputDTO> registerUser(
            @Valid
            @RequestBody UserInputDTO userInputDTO,
            BindingResult bindingResult
    ) {
        //catches errors and sends them back to the user
        CheckBindingResult.checkBindingResult(bindingResult);

        //hands it off for saving and returns the created user
        UserOutputDTO userOutputDTO = userService.registerUser(userInputDTO);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + userOutputDTO.getUsername()).toUriString());

        return ResponseEntity.created(uri).body(userOutputDTO);
    }

    @PostMapping("/{username}/shelters")
    public ResponseEntity<ShelterOutputDTO> registerShelter(
            @PathVariable String username,
            @Valid
            @RequestBody ShelterInputDTO shelterInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        User user = userService.fetchUserByID(username);

        ShelterOutputDTO shelterOutputDTO = shelterService.registerNewShelter(shelterInputDTO, user);

        return ResponseEntity.created(BuildUri.buildUri(shelterOutputDTO)).body(shelterOutputDTO);
    }


    // Gets
    @GetMapping()
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDTO> getUserByID(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserById(username));
    }


    // Puts

    @PutMapping("/{username}/admin")
    public ResponseEntity<UserOutputDTO> promoteToAdmin(@PathVariable String username) {
        return ResponseEntity.ok(userService.promoteToAdmin(username));
    }

    // Patch

    @PatchMapping("/{username}")
    public ResponseEntity<UserOutputDTO> updateUserByID(
            @PathVariable String username,
            @Valid
            @RequestBody UserPatchDTO userPatchDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        // Hands the input off for processing in the service layer.
        UserOutputDTO userOutputDTO = userService.updateUserByID(username, userPatchDTO);

        return ResponseEntity.ok(userOutputDTO);
    }

    // Deletes
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByID(@PathVariable String username) {
        return ResponseEntity.ok(userService.deleteUserByID(username));
    }

    @DeleteMapping("/{username}/admin")
    public ResponseEntity<UserOutputDTO> demoteAdmin(@PathVariable String username) {
        return ResponseEntity.ok(userService.demoteAdmin(username));
    }
}

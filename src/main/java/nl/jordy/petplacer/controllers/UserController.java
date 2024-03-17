package nl.jordy.petplacer.controllers;


import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.UserInputDTO;
import nl.jordy.petplacer.dtos.output.UserOutputDTO;
import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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


        return ResponseEntity.created(BuildUri.buildUri(userOutputDTO)).body(userOutputDTO);
    }

    // Gets
    @GetMapping()
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserOutputDTO> getUserByID(@PathVariable Long userID) {
        return ResponseEntity.ok(userService.findUserById(userID));
    }

    // Puts
    @PutMapping("/{userID}")
    public ResponseEntity<UserOutputDTO>updateUserByID(
            @PathVariable Long userID,
            @Valid
            @RequestBody UserInputDTO userInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        // Hands the input off for processing in the service layer.
        UserOutputDTO userOutputDTO = userService.updateUserByID(userID, userInputDTO);

        return ResponseEntity.ok(userOutputDTO);
    }

    // Deletes
    @DeleteMapping("/{userID}")
    public ResponseEntity<String> deleteUserByID(@PathVariable Long userID) {
        return ResponseEntity.ok(userService.deleteUserByID(userID));
    }
}

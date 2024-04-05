package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.dtos.output.UserOwnedPetOutputDTO;
import nl.jordy.petplacer.services.UserOwnedPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owned-pets")
public class UserOwnedPetController {

    private final UserOwnedPetService userOwnedPetService;

    public UserOwnedPetController(UserOwnedPetService userOwnedPetService) {
        this.userOwnedPetService = userOwnedPetService;
    }


    // Gets
    @GetMapping()
    public ResponseEntity<List<UserOwnedPetOutputDTO>> getAllUserOwnedPets() {
        return ResponseEntity.ok(userOwnedPetService.findAllUserOwnedPets());
    }

    @GetMapping("/{petID}")
    public ResponseEntity<UserOwnedPetOutputDTO> getUserOwnedPetById(@PathVariable Long petID) {
        return ResponseEntity.ok(userOwnedPetService.findUserOwnedPetById(petID));
    }
}

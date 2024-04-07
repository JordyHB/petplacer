package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.output.UserOwnedPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.UserOwnedPetPatchDTO;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.ImageService;
import nl.jordy.petplacer.services.UserOwnedPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ownedpets")
public class UserOwnedPetController {

    private final UserOwnedPetService userOwnedPetService;
    private final ImageService imageService;

    public UserOwnedPetController(UserOwnedPetService userOwnedPetService, ImageService imageService) {
        this.userOwnedPetService = userOwnedPetService;
        this.imageService = imageService;
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

    // Puts
    @PutMapping("/{petID}/image")
    public ResponseEntity<String> updateImage(
            @PathVariable Long petID,
            @RequestParam("image") MultipartFile imageFile
    ) {
        return ResponseEntity.ok(imageService.updateImage(
                imageFile,
                userOwnedPetService.fetchUserOwnedPetById(petID).getImage()
        ));
    }

    // Patches
    @PatchMapping("/{petID}")
    public ResponseEntity<UserOwnedPetOutputDTO> updateUserOwnedPet(
            @PathVariable Long petID,
            @Valid
            @RequestBody UserOwnedPetPatchDTO userOwnedPetPatchDTO,
            BindingResult bindingResult
    ) {
        CheckBindingResult.checkBindingResult(bindingResult);
        return ResponseEntity.ok(userOwnedPetService.updateUserOwnedPetById(petID, userOwnedPetPatchDTO));
    }

    @PatchMapping("/{userPetID}/image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long userPetID,
            @RequestParam("image") MultipartFile imageFile
    ) {
        return ResponseEntity.ok(imageService.uploadImageToUserPet(userPetID,imageFile));
    }

    // Deletes
    @DeleteMapping("/{petID}")
    public ResponseEntity<String> deleteUserOwnedPetByID(@PathVariable Long petID) {
        return ResponseEntity.ok(userOwnedPetService.deleteUserOwnedPetById(petID));
    }
}

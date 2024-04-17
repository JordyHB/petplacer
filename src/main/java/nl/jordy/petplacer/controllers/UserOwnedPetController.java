package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.output.UserOwnedPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.UserOwnedPetPatchDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.interfaces.ValidEnumValue;
import nl.jordy.petplacer.services.ImageService;
import nl.jordy.petplacer.services.UserOwnedPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @Validated
    @GetMapping("/filter")
    public ResponseEntity<List<UserOwnedPetOutputDTO>> getUserOwnedPetsByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String breed,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @ValidEnumValue(enumClass = GenderEnum.class, fieldName = "gender")
            @RequestParam(required = false, name = "gender") GenderEnum genderEnum,
            @RequestParam(required = false) Boolean spayedNeutered,
            @RequestParam(required = false) Boolean goodWithKids,
            @RequestParam(required = false) Boolean goodWithDogs,
            @RequestParam(required = false) Boolean goodWithCats,
            @RequestParam(required = false) String ownerUsername,
            @RequestParam(required = false) Boolean isAdopted
    ) {
        return ResponseEntity.ok(userOwnedPetService.findUserOwnedPetsByParams(
                name,
                species,
                breed,
                minAge,
                maxAge,
                genderEnum,
                spayedNeutered,
                goodWithKids,
                goodWithDogs,
                goodWithCats,
                ownerUsername,
                isAdopted
        ));
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
        return ResponseEntity.ok(imageService.uploadImageToUserPet(userPetID, imageFile));
    }

    // Deletes
    @DeleteMapping("/{petID}")
    public ResponseEntity<String> deleteUserOwnedPetByID(@PathVariable Long petID) {
        return ResponseEntity.ok(userOwnedPetService.deleteUserOwnedPetById(petID));
    }

    @DeleteMapping("/{petID}/image")
    public ResponseEntity<String> deleteImage(@PathVariable Long petID) {
        return ResponseEntity.ok(imageService.deleteImage(userOwnedPetService.fetchUserOwnedPetById(petID).getImage()));
    }
}

package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.ShelterInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPatchDTO;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.ShelterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shelters")
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    // Gets
    @GetMapping()
    public ResponseEntity<List<ShelterOutputDTO>> getAllShelters() {
        return ResponseEntity.ok(shelterService.findAllShelters());
    }

    @GetMapping("/{shelterID}")
    public ResponseEntity<ShelterOutputDTO> getShelterByID(@PathVariable Long shelterID) {
        return ResponseEntity.ok(shelterService.findShelterById(shelterID));
    }

    // Puts
    @PutMapping("/{shelterID}/managers/{username}")
    public ResponseEntity<ShelterOutputDTO> addManagerToShelter(
            @PathVariable Long shelterID,
            @PathVariable String username
    ) {
        return ResponseEntity.ok(shelterService.addManagerToShelter(shelterID, username));
    }


    // Patch
    @PatchMapping("/{shelterID}")
    public ResponseEntity<ShelterOutputDTO> updateShelterByID(
            @PathVariable Long shelterID,
            @Valid
            @RequestBody ShelterPatchDTO shelterPatchDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        return ResponseEntity.ok(shelterService.updateShelterByID(shelterID, shelterPatchDTO));
    }


    // Deletes
    @DeleteMapping("/{shelterID}")
    public ResponseEntity<String> deleteShelterByID(@PathVariable Long shelterID) {
        return ResponseEntity.ok(shelterService.deleteShelterByID(shelterID));
    }

    @DeleteMapping("/{shelterID}/managers/{username}")
    public ResponseEntity<ShelterOutputDTO> removeManagerFromShelter(
            @PathVariable Long shelterID,
            @PathVariable String username
    ) {
        return ResponseEntity.ok(shelterService.removeManagerFromShelter(shelterID, username));
    }

}

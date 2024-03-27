package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.DonationInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterInputDTO;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.dtos.output.ShelterOutputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPatchDTO;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.DonationService;
import nl.jordy.petplacer.services.ShelterPetService;
import nl.jordy.petplacer.services.ShelterService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shelters")
public class ShelterController {

    private final ShelterService shelterService;
    private final ShelterPetService shelterPetService;
    private final DonationService donationService;

    public ShelterController(
            ShelterService shelterService,
            ShelterPetService shelterPetService,
            DonationService donationService
    ) {
        this.shelterService = shelterService;
        this.shelterPetService = shelterPetService;
        this.donationService = donationService;
    }

    // Posts
    @PostMapping("{shelterID}/shelterpets")
    public ResponseEntity<ShelterPetOutputDTO> registerShelterPet(
            @PathVariable Long shelterID,
            @Valid
            @RequestBody ShelterPetInputDTO shelterPetInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        ShelterPetOutputDTO shelterPetOutputDTO = shelterPetService.registerNewShelterPet(shelterID, shelterPetInputDTO);

        return ResponseEntity.created(BuildUri.buildUri(shelterPetOutputDTO))
                .body(shelterPetOutputDTO);
    }

    @PostMapping("{shelterID}/donations")
    public ResponseEntity<DonationOutputDTO> addDonation(
            @PathVariable Long shelterID,
            @Valid
            @RequestBody DonationInputDTO donationInputDTO,
            BindingResult bindingResult
            ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        DonationOutputDTO donationOutputDTO = donationService.makeDonation(shelterID, donationInputDTO);

        return ResponseEntity.created(BuildUri.buildUri(donationOutputDTO)).body(donationOutputDTO);

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

    @GetMapping("/{shelterID}/shelterpets")
    public ResponseEntity<List<ShelterPetOutputDTO>> getShelterPetsByShelterID(@PathVariable Long shelterID) {
        return ResponseEntity.ok(shelterService.getshelterPets(shelterID));
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

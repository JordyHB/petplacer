package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.ShelterInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterOutputDTO;
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

    // Posts
    @PostMapping()
    public ResponseEntity<ShelterOutputDTO> registerShelter(
            @Valid
            @RequestBody ShelterInputDTO shelterInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        ShelterOutputDTO shelterOutputDTO =
                shelterService.registerNewShelter(shelterInputDTO);

        return ResponseEntity.created(BuildUri.buildUri(shelterOutputDTO)).body(shelterOutputDTO);
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
    @PutMapping("/{shelterID}")
    public ResponseEntity<ShelterOutputDTO> updateShelterByID(
            @PathVariable Long shelterID,
            @Valid
            @RequestBody ShelterInputDTO shelterInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        return ResponseEntity.ok(shelterService.updateShelterByID(shelterID, shelterInputDTO));
    }


    // Deletes
    @DeleteMapping("/{shelterID}")
    public ResponseEntity<String> deleteShelterByID(@PathVariable Long shelterID) {
        return ResponseEntity.ok(shelterService.deleteShelterByID(shelterID));
    }

}

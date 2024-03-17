package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.ShelterPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shelterpets")
public class ShelterPetController {

    private final ShelterPetService shelterPetService;

    public ShelterPetController(ShelterPetService shelterPetService) {
        this.shelterPetService = shelterPetService;
    }

    // Posts
    @PostMapping()
    public ResponseEntity<ShelterPetOutputDTO> registerShelterPet(
            @Valid
            @RequestBody ShelterPetInputDTO shelterPetInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        ShelterPetOutputDTO shelterPetOutputDTO =
                shelterPetService.registerNewShelterPet(shelterPetInputDTO);


        return ResponseEntity.created(BuildUri.buildUri(shelterPetOutputDTO))
                .body(shelterPetOutputDTO);
    }

    // Gets
    @GetMapping()
    public ResponseEntity<List<ShelterPetOutputDTO>> getALlShelterPets() {
        return ResponseEntity.ok(shelterPetService.findAllShelterPets());
    }

    @GetMapping("/{shelterPetID}")
    public ResponseEntity<ShelterPetOutputDTO> getShelterPetByID(@PathVariable Long shelterPetID) {
        return ResponseEntity.ok(shelterPetService.findShelterPetById(shelterPetID));
    }

    // Puts
    @PutMapping("/{shelterPetID}")
    public ResponseEntity<ShelterPetOutputDTO>updateShelterPetByID(
            @PathVariable Long shelterPetID,
            @Valid
            @RequestBody ShelterPetInputDTO shelterPetInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        ShelterPetOutputDTO shelterPetOutputDTO = shelterPetService
                .updateShelterPetByID(shelterPetID, shelterPetInputDTO);

        return ResponseEntity.ok(shelterPetOutputDTO);
    }

    // Deletes
    @DeleteMapping("/{shelterPetID}")
    public ResponseEntity<String> deleteShelterPetByID(@PathVariable Long shelterPetID) {
        return ResponseEntity.ok(shelterPetService.deleteUeShelterPetByID(shelterPetID));
    }
}

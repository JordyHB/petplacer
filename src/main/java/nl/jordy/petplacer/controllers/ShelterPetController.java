package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.AdoptionRequestInputDTO;
import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetPatchDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetStatusPatchDTO;
import nl.jordy.petplacer.helpers.BuildUri;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.models.AdoptionRequest;
import nl.jordy.petplacer.services.AdoptionRequestService;
import nl.jordy.petplacer.services.ImageService;
import nl.jordy.petplacer.services.ShelterPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("shelterpets")
public class ShelterPetController {

    private final ShelterPetService shelterPetService;
    private final AdoptionRequestService adoptionRequestService;
    private final ImageService imageService;

    public ShelterPetController(ShelterPetService shelterPetService,
                                AdoptionRequestService adoptionRequestService,
                                ImageService imageService
    ) {
        this.shelterPetService = shelterPetService;
        this.adoptionRequestService = adoptionRequestService;
        this.imageService = imageService;
    }

    // Posts
    @PostMapping("/{shelterPetID}/adoptionrequests")
    public ResponseEntity<AdoptionRequestOutputDTO> registerAdoptionRequest(
            @PathVariable Long shelterPetID,
            @Valid
            @RequestBody AdoptionRequestInputDTO adoptionRequestInputDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        AdoptionRequestOutputDTO adoptionRequestOutputDTO = adoptionRequestService
                .registerAdoptionRequest(adoptionRequestInputDTO, shelterPetID);

        return ResponseEntity.created(BuildUri.buildUri(adoptionRequestOutputDTO)).body(adoptionRequestOutputDTO);
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
    @PutMapping("/{shelterPetID}/image")
    public ResponseEntity<String> updateImage(
            @PathVariable Long shelterPetID,
            @RequestParam("image") MultipartFile imageFile
    ) {
        return ResponseEntity.ok(imageService.updateImage(
                imageFile,
                shelterPetService.fetchShelterPetByID(shelterPetID).getImage()
        ));
    }


    // Patch
    @PatchMapping("/{shelterPetID}")
    public ResponseEntity<ShelterPetOutputDTO>updateShelterPetByID(
            @PathVariable Long shelterPetID,
            @Valid
            @RequestBody ShelterPetPatchDTO shelterPetPatchDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        ShelterPetOutputDTO shelterPetOutputDTO = shelterPetService
                .updateShelterPetByID(shelterPetID, shelterPetPatchDTO);

        return ResponseEntity.ok(shelterPetOutputDTO);
    }

    @PatchMapping("/{shelterPetID}/image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long shelterPetID,
            @RequestParam("image") MultipartFile imageFile
    ) {
        return ResponseEntity.ok(imageService.uploadImageToShelterPet(shelterPetID,imageFile));
    }

    @PatchMapping("/{shelterPetID}/status")
    public ResponseEntity<ShelterPetOutputDTO> updateShelterPetStatusByID(
            @PathVariable Long shelterPetID,
            @Valid
            @RequestBody ShelterPetStatusPatchDTO shelterPetStatusPatchDTO,
            BindingResult bindingResult
    ) {
        CheckBindingResult.checkBindingResult(bindingResult);
        return ResponseEntity.ok(shelterPetService.updateShelterPetStatus(shelterPetID, shelterPetStatusPatchDTO));
    }

    // Deletes
    @DeleteMapping("/{shelterPetID}")
    public ResponseEntity<String> deleteShelterPetByID(@PathVariable Long shelterPetID) {
        return ResponseEntity.ok(shelterPetService.deleteShelterPetByID(shelterPetID));
    }

    @DeleteMapping("/{shelterPetID}/image")
    public ResponseEntity<String> deleteImage(
            @PathVariable Long shelterPetID
    ) {
        return ResponseEntity.ok(imageService.deleteImage(shelterPetService.fetchShelterPetByID(shelterPetID).getImage()));
    }
}

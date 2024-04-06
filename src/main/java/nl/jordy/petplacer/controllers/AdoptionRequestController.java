package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestPatchDTO;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.AdoptionRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("adoptionrequests")
@RestController
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;

    public AdoptionRequestController(AdoptionRequestService adoptionRequestService) {
        this.adoptionRequestService = adoptionRequestService;
    }

    // Gets
    @GetMapping()
    public ResponseEntity<List<AdoptionRequestOutputDTO>> getAllAdoptionRequests() {
        return ResponseEntity.ok(adoptionRequestService.findAllAdoptionRequests());
    }

    @GetMapping("/{adoptionRequestID}")
    public ResponseEntity<AdoptionRequestOutputDTO> getAdoptionRequestByID(@PathVariable Long adoptionRequestID) {
        return ResponseEntity.ok(adoptionRequestService.findAdoptionRequestById(adoptionRequestID));
    }

    // Patches
    @PatchMapping("/{adoptionRequestID}")
    public ResponseEntity<AdoptionRequestOutputDTO> patchAdoptionRequest(
            @PathVariable Long adoptionRequestID,
            @Valid
            @RequestBody AdoptionRequestPatchDTO adoptionRequestPatchDTO,
            BindingResult bindingResult
    ) {
        CheckBindingResult.checkBindingResult(bindingResult);
        return ResponseEntity.ok(adoptionRequestService.updateAdoptionRequestById(adoptionRequestID, adoptionRequestPatchDTO));
    }

    // Deletes
    @DeleteMapping("/{adoptionRequestID}")
    public ResponseEntity<String> deleteAdoptionRequestByID(@PathVariable Long adoptionRequestID) {
        return ResponseEntity.ok(adoptionRequestService.deleteAdoptionRequestById(adoptionRequestID));
    }

}

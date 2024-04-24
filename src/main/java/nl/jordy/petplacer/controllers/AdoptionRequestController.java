package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestPatchDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestStatusPatchDTO;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.interfaces.ValidEnumValue;
import nl.jordy.petplacer.services.AdoptionRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @Validated
    @GetMapping("/filter")
    public ResponseEntity<List<AdoptionRequestOutputDTO>> getAdoptionRequestsByFilter(
            @ValidEnumValue(enumClass = AdoptionRequestStatus.class, fieldName = "status")
            @RequestParam(required = false) AdoptionRequestStatus status,
            @RequestParam(required = false) String applicantName,
            @RequestParam(required = false) Long petID,
            @RequestParam(required = false) Long shelterID
    ) {
        return ResponseEntity.ok(adoptionRequestService.findAdoptionRequestByParams(
                status,
                applicantName,
                petID,
                shelterID
        ));
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
        return ResponseEntity.ok(
                adoptionRequestService.updateAdoptionRequestById(adoptionRequestID, adoptionRequestPatchDTO));
    }

    @PatchMapping("/{adoptionRequestID}/status")
    public ResponseEntity<AdoptionRequestOutputDTO> makeAdoptionRequestDecision(
            @PathVariable Long adoptionRequestID,
            @Valid
            @RequestBody AdoptionRequestStatusPatchDTO adoptionRequestStatusPatchDTO,
            BindingResult bindingResult
    ) {
        CheckBindingResult.checkBindingResult(bindingResult);
        return ResponseEntity.ok(
                adoptionRequestService.makeAdoptionRequestDecision(adoptionRequestID, adoptionRequestStatusPatchDTO)
        );
    }

    // Deletes
    @DeleteMapping("/{adoptionRequestID}")
    public ResponseEntity<String> deleteAdoptionRequestByID(@PathVariable Long adoptionRequestID) {
        return ResponseEntity.ok(adoptionRequestService.deleteAdoptionRequestById(adoptionRequestID));
    }
}

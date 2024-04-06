package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.services.AdoptionRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

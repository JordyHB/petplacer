package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.services.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("donations")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    // Gets
    @GetMapping()
    public ResponseEntity<List<DonationOutputDTO>> getAllDonations() {
        return ResponseEntity.ok(donationService.findAllDonations());
    }

    @GetMapping("/{donationID}")
    public ResponseEntity<DonationOutputDTO> getDonationByID(@PathVariable Long donationID) {
        return ResponseEntity.ok(donationService.findDonationById(donationID));
    }
}

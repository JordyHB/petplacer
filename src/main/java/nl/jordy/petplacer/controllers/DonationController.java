package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.output.DonationOutputDTO;
import nl.jordy.petplacer.dtos.patch.DonationPatchDTO;
import nl.jordy.petplacer.helpers.CheckBindingResult;
import nl.jordy.petplacer.services.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    // Patches
    @PatchMapping("/{donationID}")
    public ResponseEntity<DonationOutputDTO> updateDonation(
            @PathVariable Long donationID,
            @Valid
            @RequestBody DonationPatchDTO donationPatchDTO,
            BindingResult bindingResult
    ) {

        CheckBindingResult.checkBindingResult(bindingResult);

        return ResponseEntity.ok(donationService.updateDonationById(donationID, donationPatchDTO));
    }

    // Deletes
    @DeleteMapping("/{donationID}")
    public ResponseEntity<String> deleteDonation(@PathVariable Long donationID) {
      return ResponseEntity.ok(donationService.deleteDonationById(donationID));
    }
}

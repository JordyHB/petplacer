package nl.jordy.petplacer.controllers;

import jakarta.validation.Valid;
import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.services.ShelterPetService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shelterpets")
public class ShelterPetController {

    private final ShelterPetService shelterPetService;

    public ShelterPetController(ShelterPetService shelterPetService) {
        this.shelterPetService = shelterPetService;
    }

    @PostMapping()
    public ResponseEntity<String> registerShelterPet(
            @Valid
            @RequestBody ShelterPetInputDTO shelterPetInputDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult);
        }

        ShelterPet shelterPet = shelterPetService.registerNewShelterPet(shelterPetInputDTO);

        return ResponseEntity.ok(shelterPet.getId().toString());
    }
}

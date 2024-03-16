package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.helpers.MapPetDTOtoSubclass;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ShelterPetRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShelterPetService {

    private final ShelterPetRepository shelterPetRepository;

    public ShelterPetService(ShelterPetRepository shelterPetRepository) {
        this.shelterPetRepository = shelterPetRepository;
    }

    public ShelterPet registerNewShelterPet(ShelterPetInputDTO shelterPetInputDTO) {

        // Maps the DTO and adds a timestamp of arrival;
        ShelterPet shelterPet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(shelterPetInputDTO, ShelterPet.class);

        shelterPet.setDateOfArrival(new Date());
        shelterPet.setStatus(ShelterPetStatus.AVAILABLE);

        shelterPetRepository.save(shelterPet);

        return shelterPet;
    }
}

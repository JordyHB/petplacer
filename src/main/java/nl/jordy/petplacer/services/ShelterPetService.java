package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.MapPetDTOtoSubclass;
import nl.jordy.petplacer.helpers.ModelMapperHelper;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ShelterPetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelterPetService {

    private final ShelterPetRepository shelterPetRepository;

    public ShelterPetService(ShelterPetRepository shelterPetRepository) {
        this.shelterPetRepository = shelterPetRepository;
    }

    public ShelterPet fetchShelterPetByID(Long shelterPetID) {
        // Fetches the user by ID and throws an exception if it doesn't exist
        return shelterPetRepository.findById(shelterPetID).orElseThrow(
                () -> new RecordNotFoundException("No ShelterPet found with id: " + shelterPetID)
        );
    }

    public ShelterPetOutputDTO registerNewShelterPet(ShelterPetInputDTO shelterPetInputDTO) {

        // Maps the DTO and adds a timestamp of arrival;
        ShelterPet shelterPet = MapPetDTOtoSubclass.mapPetDTOtoSubclass(shelterPetInputDTO, ShelterPet.class);

        shelterPet.setDateOfArrival(new Date());
        shelterPet.setStatus(ShelterPetStatus.AVAILABLE);

        shelterPetRepository.save(shelterPet);

        return ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class);
    }

    public List<ShelterPetOutputDTO> findAllShelterPets() {

        List<ShelterPet> shelterPets = shelterPetRepository.findAll();

        List<ShelterPetOutputDTO> outputDTOS = shelterPets.stream()
                .map(shelterPet -> ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class))
                .toList();

        return outputDTOS;
    }

    public ShelterPetOutputDTO findShelterPetById(Long shelterPetID) {
        return ModelMapperHelper.getModelMapper().
                map(fetchShelterPetByID(shelterPetID), ShelterPetOutputDTO.class);
    }
}

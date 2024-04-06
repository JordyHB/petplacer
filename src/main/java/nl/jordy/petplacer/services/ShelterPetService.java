package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetPatchDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ShelterPetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterPetService {

    private final ShelterPetRepository shelterPetRepository;
    private final ShelterService shelterService;

    public ShelterPetService(ShelterPetRepository shelterPetRepository, ShelterService shelterService) {
        this.shelterPetRepository = shelterPetRepository;
        this.shelterService = shelterService;
    }

    public ShelterPet fetchShelterPetByID(Long shelterPetID) {
        // Fetches the user by ID and throws an exception if it doesn't exist
        return shelterPetRepository.findById(shelterPetID).orElseThrow(
                () -> new RecordNotFoundException("No ShelterPet found with id: " + shelterPetID)
        );
    }

    public ShelterPetOutputDTO registerNewShelterPet(Long shelterID, ShelterPetInputDTO shelterPetInputDTO) {

        // Maps the DTO and adds a timestamp of arrival;
        ShelterPet shelterPet = ModelMapperHelper.getModelMapper().map(shelterPetInputDTO, ShelterPet.class);

        shelterPet.setShelter(shelterService.fetchShelterByID(shelterID));

        shelterPetRepository.save(shelterPet);

        return ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class);
    }

    public List<ShelterPetOutputDTO> findAllShelterPets() {

        List<ShelterPet> shelterPets = shelterPetRepository.findAll();

        return shelterPets.stream()
                .map(shelterPet -> ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class))
                .toList();
    }

    public ShelterPetOutputDTO findShelterPetById(Long shelterPetID) {
        return ModelMapperHelper.getModelMapper().
                map(fetchShelterPetByID(shelterPetID), ShelterPetOutputDTO.class);
    }

    public ShelterPetOutputDTO updateShelterPetByID(Long shelterPetID, ShelterPetPatchDTO shelterPetPatchDTO) {

        ShelterPet shelterPet = fetchShelterPetByID(shelterPetID);

        ModelMapperHelper.getModelMapper().map(shelterPetPatchDTO, shelterPet);

        shelterPetRepository.save(shelterPet);
        return ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class);
    }

    public String deleteShelterPetByID(Long shelterPetID) {
        // uses private method to fetch and validate the user exists
        shelterPetRepository.delete(fetchShelterPetByID(shelterPetID));
        return "Shelter Pet: " + shelterPetID + " has been successfully deleted.";
    }

}

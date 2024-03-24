package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterOutputDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.AlreadyHasRole;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Authority;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.ShelterRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter fetchShelterByID(Long shelterID) {
        // Fetches the user by ID and throws an exception if it doesn't exist
        return shelterRepository.findById(shelterID).orElseThrow(
                () -> new RecordNotFoundException("No Shelter found with id: " + shelterID)
        );
    }

    public ShelterOutputDTO registerNewShelter(ShelterInputDTO shelterInputDTO, User user) {

        // Maps the DTO and adds a timestamp of arrival;
        Shelter shelter = ModelMapperHelper.getModelMapper().map(shelterInputDTO, Shelter.class);

        // Adds the initial manager
        shelter.getManagers().add(user);
        shelterRepository.save(shelter);

        if (!AlreadyHasRole.hasRole("ROLE_SHELTER_MANAGER")) {
            user.addAuthority(new Authority(user.getUsername(), "ROLE_SHELTER_MANAGER"));
        }

        return ModelMapperHelper.getModelMapper().map(shelter, ShelterOutputDTO.class);
    }

    public List<ShelterOutputDTO> findAllShelters() {

        List<Shelter> shelters = shelterRepository.findAll();

        return shelters.stream()
                .map(shelter -> ModelMapperHelper.getModelMapper().map(shelter, ShelterOutputDTO.class))
                .toList();
    }

    public ShelterOutputDTO findShelterById(Long shelterID) {

        return ModelMapperHelper.getModelMapper().
                map(fetchShelterByID(shelterID), ShelterOutputDTO.class);
    }

    public ShelterOutputDTO updateShelterByID(Long shelterID, ShelterInputDTO shelterInputDTO) {

        Shelter shelter = fetchShelterByID(shelterID);

        // Maps the DTO and adds a timestamp of last update;
        ModelMapperHelper.getModelMapper().map(shelterInputDTO, shelter);
        shelter.setDateOfLastUpdate(new Date());

        shelterRepository.save(shelter);

        return ModelMapperHelper.getModelMapper().map(shelter, ShelterOutputDTO.class);
    }

    public String deleteShelterByID(Long shelterID) {
        // uses private method to fetch and validate the user exists
        shelterRepository.delete(fetchShelterByID(shelterID));
        return "Shelter: " + shelterID + " has been successfully deleted.";
    }
}


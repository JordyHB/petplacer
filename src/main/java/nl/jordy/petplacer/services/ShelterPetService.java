package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterPetInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetPatchDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPetStatusPatchDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ShelterPetRepository;
import nl.jordy.petplacer.specifications.ShelterPetSpecification;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ShelterPetService {

    private final ShelterPetRepository shelterPetRepository;
    private final ShelterService shelterService;
    private final AccessValidator accessValidator;

    public ShelterPetService(
            ShelterPetRepository shelterPetRepository,
            ShelterService shelterService,
            AccessValidator accessValidator
    ) {
        this.shelterPetRepository = shelterPetRepository;
        this.shelterService = shelterService;
        this.accessValidator = accessValidator;
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

        Shelter shelter = shelterService.fetchShelterByID(shelterID);

        accessValidator.isSheltersManagerOrAdmin(accessValidator.getAuth(), shelter);

        shelterPet.setShelter(shelter);

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

    public List<ShelterPetOutputDTO> findShelterPetsByParams(
            String name,
            String species,
            String breed,
            Integer minAge,
            Integer maxAge,
            GenderEnum genderEnum,
            Boolean spayedNeutered,
            Boolean goodWithKids,
            Boolean goodWithDogs,
            Boolean goodWithCats,
            Long shelterID,
            BigDecimal minAdoptionFee,
            BigDecimal maxAdoptionFee,
            ShelterPetStatus status
    ) {

        return shelterPetRepository.findAll(
                        new ShelterPetSpecification(
                                name,
                                species,
                                breed,
                                minAge,
                                maxAge,
                                genderEnum,
                                spayedNeutered,
                                goodWithKids,
                                goodWithDogs,
                                goodWithCats,
                                shelterID,
                                minAdoptionFee,
                                maxAdoptionFee,
                                status
                        ))
                .stream()
                .map(shelterPet -> ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class))
                .toList();
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

    public ShelterPetOutputDTO updateShelterPetStatus(
            Long shelterPetID,
            ShelterPetStatusPatchDTO shelterPetStatusPatchDTO) {

        ShelterPet shelterPet = fetchShelterPetByID(shelterPetID);

        accessValidator.isSheltersManagerOrAdmin(accessValidator.getAuth(), shelterPet.getShelter());

        shelterPet.setStatus(shelterPetStatusPatchDTO.getStatus());
        shelterPet.setDateOfLastUpdate(new Date());

        shelterPetRepository.save(shelterPet);
        return ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class);
    }

}

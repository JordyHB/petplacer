package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.ShelterInputDTO;
import nl.jordy.petplacer.dtos.output.ShelterOutputDTO;
import nl.jordy.petplacer.dtos.output.ShelterPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.ShelterPatchDTO;
import nl.jordy.petplacer.exceptions.AlreadyExistsException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.AlreadyHasRole;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Authority;
import nl.jordy.petplacer.models.Shelter;
import nl.jordy.petplacer.models.User;
import nl.jordy.petplacer.repositories.ShelterRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final UserService userService;
    private final AccessValidator accessValidator;

    public ShelterService(ShelterRepository shelterRepository,
                          UserService userService,
                          AccessValidator accessValidator
    ) {
        this.userService = userService;
        this.shelterRepository = shelterRepository;
        this.accessValidator = accessValidator;
    }

    public Shelter fetchShelterByID(Long shelterID) {

        // Fetches the shelter by ID and throws an exception if it doesn't exist
        return shelterRepository.findById(shelterID).orElseThrow(
                () -> new RecordNotFoundException("No Shelter found with id: " + shelterID)
        );
    }

    public void validateShelterNameUnique(Object shelterDTO) {

        if (!(shelterDTO instanceof ShelterInputDTO) && !(shelterDTO instanceof ShelterPatchDTO)) {
            throw new IllegalArgumentException("ShelterDTO is not an instance of ShelterInputDTO");
        }

        String shelterName;
        if (shelterDTO instanceof ShelterInputDTO) {
            shelterName = ((ShelterInputDTO) shelterDTO).getShelterName();
        } else {
            shelterName = ((ShelterPatchDTO) shelterDTO).getShelterName();
        }

        if (shelterRepository.existsByShelterNameIgnoreCase(shelterName)) {
            throw new AlreadyExistsException("Shelter Name: " + shelterName + " already exists");
        }
    }

    public ShelterOutputDTO registerNewShelter(ShelterInputDTO shelterInputDTO, User user) {

        validateShelterNameUnique(shelterInputDTO);

        // Maps the DTO and adds a timestamp of arrival;
        Shelter shelter = ModelMapperHelper.getModelMapper().map(shelterInputDTO, Shelter.class);

        // Adds the initial manager
        shelter.getManagers().add(user);
        shelterRepository.save(shelter);

        if (!AlreadyHasRole.hasRole("ROLE_SHELTER_MANAGER")) {
            user.addAuthority(new Authority(user.getUsername(), "ROLE_SHELTER_MANAGER"));
            userService.saveUser(user);
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

    public ShelterOutputDTO updateShelterByID(Long shelterID, ShelterPatchDTO shelterPatchDTO) {

        // validates user is allowed to update the shelter and that shelter name is unique
        validateShelterNameUnique(shelterPatchDTO);
        accessValidator.isSheltersManagerOrAdmin(AccessValidator.getAuth(), shelterID);

        Shelter shelter = fetchShelterByID(shelterID);

        // Maps the DTO and adds a timestamp of last update;
        ModelMapperHelper.getModelMapper().map(shelterPatchDTO, shelter);
        shelter.setDateOfLastUpdate(new Date());

        shelterRepository.save(shelter);

        return ModelMapperHelper.getModelMapper().map(shelter, ShelterOutputDTO.class);
    }

    public String deleteShelterByID(Long shelterID) {

        accessValidator.isSheltersManagerOrAdmin(AccessValidator.getAuth(), shelterID);

        Shelter shelter = fetchShelterByID(shelterID);

        // removes Authority if the user is no longer a manager
        for (User user : shelter.getManagers()) {
            if (user.getManagedShelters().size() == 1) {
                user.removeAuthority(user.getAuthorities().stream()
                        .filter(a -> a.getAuthority().equals("ROLE_SHELTER_MANAGER"))
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("User: " + user.getUsername() + " is not a manager"))
                );
                userService.saveUser(user);
            }
        }

        shelterRepository.delete(shelter);
        return "Shelter: " + shelterID + " has been successfully deleted.";
    }

    public ShelterOutputDTO addManagerToShelter(Long shelterID, String username) {

        accessValidator.isSheltersManagerOrAdmin(AccessValidator.getAuth(), shelterID);

        Shelter shelter = fetchShelterByID(shelterID);
        User user = userService.fetchUserByUsername(username);
        shelter.getManagers().add(user);
        shelter.setDateOfLastUpdate(new Date());

        if (!AlreadyHasRole.fetchedUserHasRole(user, "ROLE_SHELTER_MANAGER")) {
            user.addAuthority(new Authority(user.getUsername(), "ROLE_SHELTER_MANAGER"));
            userService.saveUser(user);
        }

        shelterRepository.save(shelter);
        return ModelMapperHelper.getModelMapper().map(shelter, ShelterOutputDTO.class);
    }

    public ShelterOutputDTO removeManagerFromShelter(Long shelterID, String username) {

        accessValidator.isSheltersManagerOrAdmin(AccessValidator.getAuth(), shelterID);

        Shelter shelter = fetchShelterByID(shelterID);
        User user = userService.fetchUserByUsername(username);

        if (!shelter.getManagers().contains(user)) {
            throw new RecordNotFoundException("User: " + username + " is not a manager of Shelter: " + shelterID);
        } else if (shelter.getManagers().size() == 1) {
            throw new IllegalArgumentException("Shelter: " + shelterID + " must have at least one manager");
        }

        shelter.getManagers().remove(user);
        shelter.setDateOfLastUpdate(new Date());

        // Removes the role if the user is no longer a manager
        if (user.getManagedShelters().size() == 1) {
            user.removeAuthority(user.getAuthorities().stream()
                    // filters the authorities to find the manager role
                    .filter(a -> a.getAuthority().equals("ROLE_SHELTER_MANAGER"))
                    .findFirst()
                    .orElseThrow(() -> new RecordNotFoundException("User: " + username + " is not a manager"))
            );
            userService.saveUser(user);
        }

        shelterRepository.save(shelter);
        return ModelMapperHelper.getModelMapper().map(shelter, ShelterOutputDTO.class);
    }

    public List<ShelterPetOutputDTO> getshelterPets(Long shelterID) {
        Shelter shelter = fetchShelterByID(shelterID);
        return shelter.getShelterPets().stream()
                .map(shelterPet -> ModelMapperHelper.getModelMapper().map(shelterPet, ShelterPetOutputDTO.class))
                .toList();
    }
}


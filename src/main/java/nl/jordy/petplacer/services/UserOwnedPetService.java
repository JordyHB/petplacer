package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.UserOwnedPetInputDTO;
import nl.jordy.petplacer.dtos.output.UserOwnedPetOutputDTO;
import nl.jordy.petplacer.dtos.patch.UserOwnedPetPatchDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.UserOwnedPet;
import nl.jordy.petplacer.repositories.UserOwnedPetRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserOwnedPetService {

    private final UserOwnedPetRepository userOwnedPetRepository;
    private final UserService userService;

    public UserOwnedPetService(UserOwnedPetRepository userOwnedPetRepository, UserService userService) {
        this.userOwnedPetRepository = userOwnedPetRepository;
        this.userService = userService;
    }

    public UserOwnedPet fetchUserOwnedPetById(Long id) {
        return userOwnedPetRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No user owned pet found with id: " + id)
        );
    }

    public UserOwnedPetOutputDTO registerUserOwnedPet(UserOwnedPetInputDTO userOwnedPetDTO, String username) {

        // checks if the request is made by the user that owns the pet or an admin
        AccessValidator.isUserOrAdmin(AccessValidator.getAuth(), username);

        UserOwnedPet userOwnedPet = ModelMapperHelper.getModelMapper().map(userOwnedPetDTO, UserOwnedPet.class);

        // fetches the user that is the current owner of the pet
        userOwnedPet.setCurrentOwner(userService.fetchUserByUsername(username));

        userOwnedPetRepository.save(userOwnedPet);

        return ModelMapperHelper.getModelMapper().map(userOwnedPet, UserOwnedPetOutputDTO.class);
    }

    public List<UserOwnedPetOutputDTO> findAllUserOwnedPets() {
        List<UserOwnedPet> userOwnedPets = userOwnedPetRepository.findAll();
        return userOwnedPets.stream()
                .map(userOwnedPet -> ModelMapperHelper.getModelMapper().map(userOwnedPet, UserOwnedPetOutputDTO.class))
                .toList();
    }

    public UserOwnedPetOutputDTO findUserOwnedPetById(Long id) {

        UserOwnedPet requestedUserOwnedPet = fetchUserOwnedPetById(id);

        // checks if the request is made by the user that owns the pet or an admin
        AccessValidator.isUserOrAdmin(AccessValidator.getAuth(), requestedUserOwnedPet.getCurrentOwner().getUsername());

        UserOwnedPet userOwnedPet = fetchUserOwnedPetById(id);
        return ModelMapperHelper.getModelMapper().map(userOwnedPet, UserOwnedPetOutputDTO.class);
    }

    public UserOwnedPetOutputDTO updateUserOwnedPetById(Long petID, UserOwnedPetPatchDTO userOwnedPetPatchDTO) {

        UserOwnedPet userOwnedPet = fetchUserOwnedPetById(petID);

        // checks if the request is made by the user that owns the pet or an admin
        AccessValidator.isUserOrAdmin(AccessValidator.getAuth(), userOwnedPet.getCurrentOwner().getUsername());

        ModelMapperHelper.getModelMapper().map(userOwnedPetPatchDTO, userOwnedPet);
        userOwnedPet.setDateOfLastUpdate(new Date());

        userOwnedPetRepository.save(userOwnedPet);
        return ModelMapperHelper.getModelMapper().map(userOwnedPet, UserOwnedPetOutputDTO.class);
    }

    public String deleteUserOwnedPetById(Long petID) {

        UserOwnedPet userOwnedPet = fetchUserOwnedPetById(petID);

        // checks if the request is made by the user that owns the pet or an admin
        AccessValidator.isUserOrAdmin(AccessValidator.getAuth(), userOwnedPet.getCurrentOwner().getUsername());

        userOwnedPetRepository.deleteById(petID);
        return "User owned pet with id: " + petID + " has been deleted";
    }
}



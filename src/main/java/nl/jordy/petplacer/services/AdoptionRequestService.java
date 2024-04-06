package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.AdoptionRequestInputDTO;
import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.AdoptionRequest;
import nl.jordy.petplacer.repositories.AdoptionRequestRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

@Service
public class AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;
    private final UserService userService;
    private final ShelterPetService shelterPetService;

    public AdoptionRequestService(
            AdoptionRequestRepository adoptionRequestRepository,
            UserService userService,
            ShelterPetService shelterPetService
    ) {
        this.adoptionRequestRepository = adoptionRequestRepository;
        this.userService = userService;
        this.shelterPetService = shelterPetService;
    }

    public AdoptionRequest fetchAdoptionRequestById(Long id) {
        return adoptionRequestRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No adoption request found with id: " + id)
        );
    }

    public AdoptionRequestOutputDTO registerAdoptionRequest(
            AdoptionRequestInputDTO adoptionRequestInputDTO,
            Long shelterPetID
    ) {

        AdoptionRequest adoptionRequest =
                ModelMapperHelper.getModelMapper().map(adoptionRequestInputDTO, AdoptionRequest.class);

        adoptionRequest.setAdoptionApplicant(userService.fetchUserByUsername(AccessValidator.getAuth().getName()));
        adoptionRequest.setRequestedPet(shelterPetService.fetchShelterPetByID(shelterPetID));

        adoptionRequestRepository.save(adoptionRequest);

        return ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class);
    }

}

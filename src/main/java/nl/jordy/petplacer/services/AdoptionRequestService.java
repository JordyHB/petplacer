package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.AdoptionRequestInputDTO;
import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestPatchDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestStatusPatchDTO;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.AdoptionRequest;
import nl.jordy.petplacer.repositories.AdoptionRequestRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public List<AdoptionRequestOutputDTO> findAllAdoptionRequests() {

        List<AdoptionRequest> adoptionRequests = adoptionRequestRepository.findAll();

        return adoptionRequests.stream()
                .map(adoptionRequest -> ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class))
                .toList();
    }

    public AdoptionRequestOutputDTO findAdoptionRequestById(Long id) {

        AdoptionRequest requestedAdoptionRequest = fetchAdoptionRequestById(id);

        return ModelMapperHelper.getModelMapper().map(requestedAdoptionRequest, AdoptionRequestOutputDTO.class);
    }

    public AdoptionRequestOutputDTO updateAdoptionRequestById(Long adoptionRequestID, AdoptionRequestPatchDTO adoptionRequestPatchDTO) {

        AdoptionRequest adoptionRequest = fetchAdoptionRequestById(adoptionRequestID);

        // checks if the request is made by the user that owns the pet or an admin
        AccessValidator.isUserOrAdmin(AccessValidator.getAuth(), adoptionRequest.getAdoptionApplicant().getUsername());

        ModelMapperHelper.getModelMapper().map(adoptionRequestPatchDTO, adoptionRequest);

        adoptionRequestRepository.save(adoptionRequest);

        return ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class);
    }

    public String deleteAdoptionRequestById(Long adoptionRequestID) {

        AdoptionRequest adoptionRequest = fetchAdoptionRequestById(adoptionRequestID);

        adoptionRequestRepository.delete(adoptionRequest);

        return "Adoption request with id: " + adoptionRequestID + " has been deleted";
    }

    public AdoptionRequestOutputDTO makeAdoptionRequestDecision(
            Long adoptionRequestID,
            AdoptionRequestStatusPatchDTO adoptionRequestStatusPatchDTO) {

        AdoptionRequest adoptionRequest = fetchAdoptionRequestById(adoptionRequestID);

        AccessValidator.isSheltersManagerOrAdmin(
                AccessValidator.getAuth(),
                adoptionRequest.getRequestedPet().getShelter()
        );

        adoptionRequest.setStatus(adoptionRequestStatusPatchDTO.getStatus());
        adoptionRequest.setDecisionDate(new Date());

        adoptionRequestRepository.save(adoptionRequest);

        return ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class);
    }
}

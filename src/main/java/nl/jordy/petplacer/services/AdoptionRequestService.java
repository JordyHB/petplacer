package nl.jordy.petplacer.services;

import nl.jordy.petplacer.dtos.input.AdoptionRequestInputDTO;
import nl.jordy.petplacer.dtos.output.AdoptionRequestOutputDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestPatchDTO;
import nl.jordy.petplacer.dtos.patch.AdoptionRequestStatusPatchDTO;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.exceptions.CustomAccessDeniedException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.AdoptionRequest;
import nl.jordy.petplacer.repositories.AdoptionRequestRepository;
import nl.jordy.petplacer.specifications.AdoptionRequestSpecification;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;
    private final UserService userService;
    private final ShelterPetService shelterPetService;
    private final AccessValidator accessValidator;

    public AdoptionRequestService(
            AdoptionRequestRepository adoptionRequestRepository,
            UserService userService,
            ShelterPetService shelterPetService,
            AccessValidator accessValidator
    ) {
        this.adoptionRequestRepository = adoptionRequestRepository;
        this.userService = userService;
        this.shelterPetService = shelterPetService;
        this.accessValidator = accessValidator;
    }

    public AdoptionRequest fetchAdoptionRequestById(Long id) {

        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No adoption request found with id: " + id)
        );

        // blocks access to the adoption request if the user is not the applicant, an admin or the shelter manager
        if (!accessValidator.canAccessAdoptionInfo(accessValidator.getAuth(), adoptionRequest)) {
            throw new CustomAccessDeniedException();
        }

        return adoptionRequest;
    }

    public AdoptionRequestOutputDTO registerAdoptionRequest(
            AdoptionRequestInputDTO adoptionRequestInputDTO,
            Long shelterPetID
    ) {

        AdoptionRequest adoptionRequest =
                ModelMapperHelper.getModelMapper().map(adoptionRequestInputDTO, AdoptionRequest.class);

        adoptionRequest.setAdoptionApplicant(userService.fetchUserByUsername(accessValidator.getAuth().getName()));
        adoptionRequest.setRequestedPet(shelterPetService.fetchShelterPetByID(shelterPetID));

        adoptionRequestRepository.save(adoptionRequest);

        return ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class);
    }

    public List<AdoptionRequestOutputDTO> findAllAdoptionRequests() {

        List<AdoptionRequest> adoptionRequests = adoptionRequestRepository.findAll();

        return adoptionRequests.stream()
                .map(adoptionRequest ->
                        ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class))
                .toList();
    }

    public AdoptionRequestOutputDTO findAdoptionRequestById(Long id) {

        AdoptionRequest requestedAdoptionRequest = fetchAdoptionRequestById(id);

        return ModelMapperHelper.getModelMapper().map(requestedAdoptionRequest, AdoptionRequestOutputDTO.class);
    }

    public List<AdoptionRequestOutputDTO> findAdoptionRequestByParams(
            AdoptionRequestStatus status,
            String applicantName,
            Long petID,
            Long shelterID
    ) {

        AdoptionRequestSpecification adoptionRequestSpecification = new AdoptionRequestSpecification(
                status,
                applicantName,
                petID,
                shelterID
        );

        List<AdoptionRequest> adoptionRequests = adoptionRequestRepository.findAll(adoptionRequestSpecification);

        return adoptionRequests.stream()
                .filter(adoptionRequest ->
                        accessValidator.canAccessAdoptionInfo(
                                accessValidator.getAuth(), adoptionRequest
                        ))
                .map(
                        adoptionRequest -> ModelMapperHelper.getModelMapper()
                                .map(adoptionRequest, AdoptionRequestOutputDTO.class))
                .toList();
    }

    public AdoptionRequestOutputDTO updateAdoptionRequestById(Long adoptionRequestID, AdoptionRequestPatchDTO adoptionRequestPatchDTO) {

        AdoptionRequest adoptionRequest = fetchAdoptionRequestById(adoptionRequestID);

        // checks if the request is made by the user that owns the pet or an admin
        accessValidator.isUserOrAdmin(accessValidator.getAuth(), adoptionRequest.getAdoptionApplicant().getUsername());

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

        accessValidator.isSheltersManagerOrAdmin(
                accessValidator.getAuth(),
                adoptionRequest.getRequestedPet().getShelter()
        );

        // updates the status of the pet based on the decision
        if (adoptionRequestStatusPatchDTO.getStatus().name().equals("APPROVED")) {
            adoptionRequest.getRequestedPet().setStatus(ShelterPetStatus.RESERVED);
        } else if (adoptionRequestStatusPatchDTO.getStatus().name().equals("REJECTED")) {
            adoptionRequest.getRequestedPet().setStatus(ShelterPetStatus.AVAILABLE);
        } else {
            adoptionRequest.getRequestedPet().setStatus(ShelterPetStatus.AVAILABLE);
        }

        if (!adoptionRequestStatusPatchDTO.getStatus().name().equals("PENDING")) {
            adoptionRequest.setDecisionDate(new Date());
        } else {
            // if the status is pending, the decision date is set to null
            adoptionRequest.setDecisionDate(null);
        }

        adoptionRequest.setStatus(adoptionRequestStatusPatchDTO.getStatus());

        adoptionRequestRepository.save(adoptionRequest);

        return ModelMapperHelper.getModelMapper().map(adoptionRequest, AdoptionRequestOutputDTO.class);
    }
}

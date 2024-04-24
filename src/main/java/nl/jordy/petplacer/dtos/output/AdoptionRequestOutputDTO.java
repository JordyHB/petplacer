package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.dtos.summary.ShelterPetSummaryDTO;
import nl.jordy.petplacer.dtos.summary.UserSummaryDTO;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.util.Date;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
public class AdoptionRequestOutputDTO implements HasFetchableId {

    private Long id;
    private Date submissionDate;
    private String requestMessage;
    private AdoptionRequestStatus status;
    private Date decisionDate;
    private UserSummaryDTO adoptionApplicant;
    private ShelterPetSummaryDTO requestedPet;
}

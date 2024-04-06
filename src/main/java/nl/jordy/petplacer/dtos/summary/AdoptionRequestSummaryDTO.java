package nl.jordy.petplacer.dtos.summary;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;

import java.util.Date;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
public class AdoptionRequestSummaryDTO {

    private Long id;
    private Date submissionDate;
    private String requestMessage;
    private AdoptionRequestStatus status;
    private Date decisionDate;

    @JsonIdentityReference(alwaysAsId = true)
    private UserSummaryDTO adoptionApplicant;
    @JsonIdentityReference(alwaysAsId = true)
    private ShelterPetSummaryDTO requestedPet;
}

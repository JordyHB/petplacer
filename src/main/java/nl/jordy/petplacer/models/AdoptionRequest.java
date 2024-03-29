package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;

import java.util.Date;

@Entity
@Data
@Table(name = "adoption_requests")
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    private ShelterPet requestedPet;

    @ManyToOne
    private User adoptionApplicant;

    @ManyToOne
    private Shelter requestedPetShelter;

    private Date submission_date;
    private String requestMessage;

    @Enumerated(EnumType.STRING)
    private AdoptionRequestStatus status;

    // this will be filled after the status is approved for easy record keeping
    @OneToOne(mappedBy = "approvedNewHome")
    private ShelterPet rehomedPet;
}

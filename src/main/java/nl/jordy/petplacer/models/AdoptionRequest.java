package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "adoption_requests")
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // blocks the setter for ID
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    private ShelterPet requestedPet;

    @ManyToOne
    private User adoptionApplicant;

    private Date submissionDate = new Date();
    private Date decisionDate;
    private String requestMessage;

    @ValidEnumValue(enumClass = AdoptionRequestStatus.class)
    @Enumerated(EnumType.STRING)
    private AdoptionRequestStatus status = AdoptionRequestStatus.PENDING;
}

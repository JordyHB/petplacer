package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.*;
import nl.jordy.petplacer.enums.ShelterPetStatus;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "shelter_pets")
public class ShelterPet extends Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShelterPetStatus status = ShelterPetStatus.AVAILABLE;

    private Date dateOfRegistration = new Date();
    private Date dateOfLastUpdate = new Date();
    private int monthsInShelter;
    private String medicalHistory;
    private String specialNeeds;
    private String previousSituation;

    @ManyToOne(fetch = FetchType.EAGER)
    private Shelter shelter;

    @OneToMany(mappedBy = "requestedPet")
    private List<AdoptionRequest> adoptionRequests;

    //filled when an adoption request is fulfilled

    @OneToOne
    private AdoptionRequest approvedNewHome;

    private Date rehomeDate;
}

package nl.jordy.petplacer.models;

import jakarta.persistence.*;
import lombok.*;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.interfaces.ValidEnumValue;

import java.math.BigDecimal;
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

    @ValidEnumValue(enumClass = ShelterPetStatus.class)
    @Enumerated(EnumType.STRING)
    private ShelterPetStatus status = ShelterPetStatus.AVAILABLE;

    private BigDecimal adoptionFee;
    private Date dateOfRegistration = new Date();
    private Date dateOfLastUpdate = new Date();
    private int monthsInShelter;
    private String medicalHistory;
    private String specialNeeds;
    private String previousSituation;

    @OneToOne(mappedBy = "shelterPet", cascade = CascadeType.ALL)
    private Image image;

    @ManyToOne(fetch = FetchType.EAGER)
    private Shelter shelter;

    @OneToMany(
            mappedBy = "requestedPet",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AdoptionRequest> adoptionRequests;
}

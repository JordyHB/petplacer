package nl.jordy.petplacer.dtos.summary;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter

public class ShelterPetSummaryDTO {
    private Long id;
    private String name;
    private int age;
    private String species;
    private String breed;
    private String color;
    private GenderEnum gender;
    private ShelterPetStatus status;
    private String size;
    private String description;
    private boolean isSpayedNeutered;
    private boolean isGoodWithKids;
    private boolean isGoodWithDogs;
    private boolean isGoodWithCats;
    private Date dateOfRegistration;
    private int monthsInShelter;
    private String medicalHistory;
    private String specialNeeds;
    private String previousSituation;

    @JsonIdentityReference(alwaysAsId = true)
    private ShelterSummaryDTO shelter;

    // this gets filled later on
    private List<Long> adoptionRequests;
}

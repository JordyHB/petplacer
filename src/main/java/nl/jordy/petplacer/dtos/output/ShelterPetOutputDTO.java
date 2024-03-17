package nl.jordy.petplacer.dtos.output;

import lombok.Data;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.util.Date;
import java.util.List;

@Data
public class ShelterPetOutputDTO implements HasFetchableId {

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
    private Date dateOfArrival;
    private int monthsInShelter;
    private String medicalHistory;
    private String specialNeeds;
    private String previousSituation;
    private Long shelterId;
    private List<Long> adoptionRequests;
    private Long approvedNewHome;
    private Date rehomeDate;
}

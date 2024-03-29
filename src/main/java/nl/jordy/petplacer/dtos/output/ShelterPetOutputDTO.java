package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.util.Date;
import java.util.List;

@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
    private Date dateOfRegistration;
    private Date dateOfLastUpdate;
    private int monthsInShelter;
    private String medicalHistory;
    private String specialNeeds;
    private String previousSituation;
    private ShelterOutputDTO shelter;
    private List<Long> adoptionRequests;
    private Long approvedNewHome;
    private Date rehomeDate;
}

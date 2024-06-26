package nl.jordy.petplacer.dtos.output;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import nl.jordy.petplacer.dtos.summary.AdoptionRequestSummaryDTO;
import nl.jordy.petplacer.dtos.summary.ShelterSummaryDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.enums.ShelterPetStatus;
import nl.jordy.petplacer.interfaces.HasFetchableId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
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
    private BigDecimal adoptionFee;
    private int monthsInShelter;
    private String medicalHistory;
    private String specialNeeds;
    private String previousSituation;
    private String imageLink;
    private List<AdoptionRequestSummaryDTO> adoptionRequests;
    private ShelterSummaryDTO shelter;
}

package nl.jordy.petplacer.dtos.summary;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.GenderEnum;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Getter
@Setter
public class UserOwnedPetSummaryDTO {
    private Long id;
    private String name;
    private int age;
    private String species;
    private String breed;
    private String color;
    private GenderEnum gender;
    private String size;
    private String description;
    private boolean isSpayedNeutered;
    private boolean isGoodWithKids;
    private boolean isGoodWithDogs;
    private boolean isGoodWithCats;
    private boolean isAdopted;
    private int yearsOwned;
    private String imageLink;

    @JsonIdentityReference(alwaysAsId = true)
    private UserSummaryDTO currentOwner;
}

package nl.jordy.petplacer.dtos.output;

import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.dtos.summary.UserSummaryDTO;
import nl.jordy.petplacer.enums.GenderEnum;
import nl.jordy.petplacer.interfaces.HasFetchableId;

@Getter
@Setter
public class UserOwnedPetOutputDTO implements HasFetchableId {
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

    private UserSummaryDTO currentOwner;
}

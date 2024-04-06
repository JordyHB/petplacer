package nl.jordy.petplacer.dtos.patch;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOwnedPetPatchDTO extends PetPatchDTO {

    private boolean isAdopted;

    @Min(value = 0, message = "years owned must be 0 or greater")
    private int yearsOwned;

}

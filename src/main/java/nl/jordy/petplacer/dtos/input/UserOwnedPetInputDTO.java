package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOwnedPetInputDTO extends PetInputDTO{


    @NotNull(message = "info if the pet is adopted is required")
    private boolean isAdopted;

    @NotNull(message = "years owned is required")
    @Min(value = 0, message = "years owned must be 0 or greater")
    private int yearsOwned;

}

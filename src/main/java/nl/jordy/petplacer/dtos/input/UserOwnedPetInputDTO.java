package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UserOwnedPetInputDTO {

    // nested DTO with the info for the super class
    private PetInputDTO pet;

    @NotNull(message = "Owner ID is required")
    @Min(value = 1, message = "Owner ID must be greater than 0")
    private Long ownerId;

    @NotNull(message = "info if you are the first owner is required")
    private boolean firstOwner;

    @NotNull(message = "info if the pet is adopted is required")
    private boolean isAdopted;

    @NotNull(message = "years owned is required")
    @Min(value = 0, message = "years owned must be 0 or greater")
    private int yearsOwned;

}

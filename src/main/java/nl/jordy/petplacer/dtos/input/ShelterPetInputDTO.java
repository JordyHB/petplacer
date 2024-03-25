package nl.jordy.petplacer.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShelterPetInputDTO {

    // nested DTO with the info for the super class
    @Valid
    @JsonProperty("pet")
    private PetInputDTO pet;

    @NotNull(message = "Months in shelter is required")
    @Min(value = 0, message = "Months in shelter must be 0 or greater")
    private int monthsInShelter;

    @Size(max = 500, message = "Medical history message is too long")
    private String medicalHistory;

    @Size(max = 500, message = "Special needs message is too long")
    private String specialNeeds;

    @NotNull(message = "Previous situation is required")
    @Size(max = 500, message = "Previous situation message is too long")
    private String previousSituation;
}

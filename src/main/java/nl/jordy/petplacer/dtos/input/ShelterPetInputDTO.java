package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShelterPetInputDTO extends PetInputDTO {

    @NotNull(message = "Adoption fee is required")
    @Min(value = 0, message = "Adoption fee must be 0 or greater")
    @Max(value = 1000, message = "Adoption fee must be 1000 or less")
    private double adoptionFee;

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

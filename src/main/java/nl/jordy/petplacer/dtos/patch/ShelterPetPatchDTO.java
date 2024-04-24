package nl.jordy.petplacer.dtos.patch;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ShelterPetPatchDTO extends PetPatchDTO {

    @Min(value = 0, message = "Adoption fee must be 0 or greater")
    @Max(value = 1000, message = "Adoption fee must be 1000 or less")
    private BigDecimal adoptionFee;

    @Min(value = 0, message = "Months in shelter must be 0 or greater")
    private int monthsInShelter;

    @Size(max = 500, message = "Medical history message is too long")
    private String medicalHistory;

    @Size(max = 500, message = "Special needs message is too long")
    private String specialNeeds;

    @Size(max = 500, message = "Previous situation message is too long")
    private String previousSituation;

}

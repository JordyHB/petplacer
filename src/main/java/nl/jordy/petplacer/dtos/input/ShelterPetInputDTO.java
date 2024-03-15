package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import nl.jordy.petplacer.enums.ShelterPetStatus;

import java.util.Date;

@Data
public class ShelterPetInputDTO {

    @NotNull(message = "ID is required")
    @Min(value = 1, message = "ID must be greater than 0")
    private Long id;

    @NotNull(message = "Status is required")
    private ShelterPetStatus status;

    @NotNull(message = "Date of arrival is required")
    @PastOrPresent(message = "Date of arrival must be in the past or present")
    private Date dateOfArrival;

    @NotNull(message = "Months in shelter is required")
    @Min(value = 0, message = "Months in shelter must be 0 or greater")
    private int monthsInShelter;

    @Size(max = 500, message = "Medical history message is too long")
    private String medicalHistory;

    @Size(max = 500, message = "Special needs message is too long")
    private String specialNeeds;

    @Size(max = 500, message = "Previous situation messasge is too long")
    private String previousSituation;
}

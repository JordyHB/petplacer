package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdoptionRequestInputDTO {

    @NotNull(message = "Requested pet ID is required")
    @Min(value = 1, message = "Requested pet ID must be greater than 0")
    private Long requestedPetId;
    @NotNull(message = "Adoption applicant ID is required")
    @Min(value = 1, message = "Adoption applicant ID must be greater than 0")
    private Long adoptionApplicantId;
    @Min(value = 1, message = "Requested pet shelter ID must be greater than 0")
    @NotNull(message = "Requested pet shelter ID is required")
    private Long requestedPetShelterId;
    @NotBlank(message = "Request message is required")
    @Max(value = 500, message = "Request message is too long")
    private String requestMessage;
}

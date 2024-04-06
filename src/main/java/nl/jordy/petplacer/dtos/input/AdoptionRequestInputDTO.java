package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionRequestInputDTO {

    @NotBlank(message = "Request message is required")
    @Max(value = 500, message = "Request message is too long")
    private String requestMessage;
}

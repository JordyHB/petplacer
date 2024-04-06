package nl.jordy.petplacer.dtos.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionRequestInputDTO {

    @NotBlank(message = "Request message is required")
    @Size(max = 500, message = "Request message is too long")
    private String requestMessage;
}

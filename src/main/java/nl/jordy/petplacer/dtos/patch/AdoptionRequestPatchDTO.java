package nl.jordy.petplacer.dtos.patch;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionRequestPatchDTO {

    @Size(max = 500, message = "Request message is too long")
    private String requestMessage;
}

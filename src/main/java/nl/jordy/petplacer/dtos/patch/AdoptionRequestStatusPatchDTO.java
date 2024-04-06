package nl.jordy.petplacer.dtos.patch;

import lombok.Getter;
import lombok.Setter;
import nl.jordy.petplacer.enums.AdoptionRequestStatus;

@Getter
@Setter
public class AdoptionRequestStatusPatchDTO {
    private AdoptionRequestStatus status;

}
